package me.pi.cloud.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import me.pi.cloud.auth.extension.*;
import me.pi.cloud.auth.extension.password.OAuth2PasswordAuthenticationConverter;
import me.pi.cloud.auth.extension.password.OAuth2PasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Spring Authorization Server configuration
 *
 * @author ZnPi
 * @date 2022-10-20
 */
@Configuration
public class AuthorizationServerConfiguration {
    /**
     * A Spring Security filter chain for the Protocol Endpoints.
     *
     * @param http the httpSecurity
     * @return the securityFilterChain
     * @throws Exception the exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http.apply(authorizationServerConfigurer);

        authorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        .accessTokenRequestConverters(authenticationConverters ->
                                authenticationConverters.add(new OAuth2PasswordAuthenticationConverter()))
                        .accessTokenResponseHandler(responseDataAuthenticationSuccessHandler())
                        .errorResponseHandler(authenticationFailureHandler()))
                .clientAuthentication(clientAuthentication -> clientAuthentication
                        .errorResponseHandler(authenticationFailureHandler()));

        DefaultSecurityFilterChain chain = http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                )
                .csrf(CsrfConfigurer::disable)
                .build();

        addingAdditionalAuthenticationProvider(http);

        return chain;
    }

    /**
     * A Spring Security filter chain for authentication.
     *
     * @param http the httpSecurity
     * @return the securityFilterChain
     * @throws Exception the exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          OAuth2LogoutHandler logoutHandler)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/actuator/**", "/v3/api-docs/**",
                                "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(new OAuth2LogoutSuccessHandler())
                ).csrf().disable();
        ;

        return http.build();
    }

    /**
     * An instance of UserDetailsService for retrieving users to authenticate.
     *
     * @return the userDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        return new OAuth2UserDetailsManager(jdbcTemplate);
    }

    /**
     * An instance of RegisteredClientRepository for managing clients.
     *
     * @return the registeredClientRepository
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    /**
     * An instance of com.nimbusds.jose.jwk.source.JWKSource for signing access tokens.
     *
     * @return the jwkSource
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * An instance of JwtDecoder for decoding signed access tokens.
     *
     * @param jwkSource the jwkSource
     * @return the jwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * An instance of AuthorizationServerSettings to configure Spring Authorization Server.
     *
     * @return the authorizationServerSettings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler responseDataAuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler();
    }

    @Bean
    public OAuth2AuthenticationFailureHandler authenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler();
    }

    private static void addingAdditionalAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        OAuth2TokenGenerator<?> tokenGenerator = http.getSharedObject(OAuth2TokenGenerator.class);

        OAuth2PasswordAuthenticationProvider passwordAuthenticationProvider =
                new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator);
        http.authenticationProvider(passwordAuthenticationProvider);
    }

    /**
     * An instance of java.security.KeyPair with keys generated on startup used to create the JWKSource above.
     *
     * @return the keyPair
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