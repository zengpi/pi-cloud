/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.cloud.pi.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.auth.extension.*;
import me.cloud.pi.auth.extension.password.OAuth2PasswordAuthenticationConverter;
import me.cloud.pi.auth.extension.password.OAuth2PasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

/**
 * @author ZnPi
 * @date 2022-10-20
 */
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfiguration {
    private final OAuth2LogoutHandler oAuth2LogoutHandler;

    /**
     * Spring Security 过滤器链，用于配置协议端点
     * @param http /
     * @return /
     * @throws Exception /
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      PiAuthenticationSuccessHandler piAuthenticationSuccessHandler)
            throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        http.apply(authorizationServerConfigurer);

        authorizationServerConfigurer
                .tokenEndpoint(tokenEndpoint ->
                        tokenEndpoint
                            .accessTokenRequestConverter(authenticationConverter())
                            .accessTokenResponseHandler(piAuthenticationSuccessHandler)
                            .errorResponseHandler(new PiAuthenticationFailureHandler())
                )
                .clientAuthentication(clientAuthentication ->
                        clientAuthentication
                                .errorResponseHandler(new PiAuthenticationFailureHandler()));

        DefaultSecurityFilterChain chain = http
                .requestMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().authenticated()
                )
                .csrf().disable()
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login"))
                )
                .build();

        // 添加自定义 AuthenticationProvider
        addingAdditionalAuthenticationProvider(http);

        return chain;
    }

    /**
     * Spring Security 过滤器链，用于身份验证
     * @param http /
     * @return /
     * @throws Exception /
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .addLogoutHandler(oAuth2LogoutHandler)
                        .logoutSuccessHandler(new PiLogoutSuccessHandler())
                ).csrf().disable();

        return http.build();
    }

    /**
     * UserDetailsService 实例，用于检索要进行身份验证的用户
     * @return /
     */
    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        return new PiUserDetailsManager(jdbcTemplate);
    }

    /**
     * RegisteredClientRepository 实例，用于管理客户端
     * @return /
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new PiJdbcRegisteredClientRepository(jdbcTemplate);
    }

    /**
     * com.nimbusds.jose.jwk.source.JWKSource 实例，用于签署访问令牌
     * @return /
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID("pi-cloud")
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * （REQUIRED）包含OAuth2授权服务器的配置设置。它指定协议端点的URI以及 issuer 标识符。
     * @return /
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer("http://pi:9731/auth").build();
    }

    /**
     * 自定义 AuthenticationConverter
     * @return /
     */
    private AuthenticationConverter authenticationConverter(){
        return new DelegatingAuthenticationConverter(
                Arrays.asList(
                        new OAuth2AuthorizationCodeAuthenticationConverter(),
                        new OAuth2RefreshTokenAuthenticationConverter(),
                        new OAuth2ClientCredentialsAuthenticationConverter(),
                        new OAuth2PasswordAuthenticationConverter()
                )
        );
    }

    /**
     * 添加自定义 AuthenticationProvider
     * @param http /
     */
    private void addingAdditionalAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        OAuth2TokenGenerator<?> tokenGenerator = http.getSharedObject(OAuth2TokenGenerator.class);
        OAuth2PasswordAuthenticationProvider passwordAuthenticationProvider =
                new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator);
        http.authenticationProvider(passwordAuthenticationProvider);
    }

    /**
     * java.security.KeyPair 实例，加载 JWT 公钥和私钥
     * @return /
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                    new ClassPathResource("pi-cloud.jks"),
                    "123456".toCharArray()
            );
            keyPair = factory.getKeyPair("pi-cloud-jwt", "123456".toCharArray());
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}