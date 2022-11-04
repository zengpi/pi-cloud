package me.cloud.pi.auth.extension.password;

import me.cloud.pi.auth.exception.PiOAuth2AuthencticationException;
import me.cloud.pi.common.web.enums.ResponseStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.ProviderContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.*;

import static me.cloud.pi.auth.util.OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
public class OAuth2PasswordAuthenticationProvider implements AuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";
    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * Constructs an {@code OAuth2PasswordAuthenticationProvider} using the provided parameters.
     *
     * @param authenticationManager the authorization manager
     * @param authorizationService  the authorization service
     * @param tokenGenerator        the token generator
     */
    public OAuth2PasswordAuthenticationProvider(AuthenticationManager authenticationManager,
                                                OAuth2AuthorizationService authorizationService,
                                                OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2PasswordAuthenticationToken passwordAuthentication =
                (OAuth2PasswordAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal =
                getAuthenticatedClientElseThrowInvalidClient(passwordAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        assert registeredClient != null;

        if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        // Default to configured scopes
        Set<String> authorizedScopes = registeredClient.getScopes();
        if (!CollectionUtils.isEmpty(passwordAuthentication.getScopes())) {
            for (String requestedScope : passwordAuthentication.getScopes()) {
                if (!registeredClient.getScopes().contains(requestedScope)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
                }
            }
            authorizedScopes = new LinkedHashSet<>(passwordAuthentication.getScopes());
        }

        // 认证用户名密码
        Authentication usernamePasswordAuthentication = userNamePasswordAuthenticate(passwordAuthentication);

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = tokenContextBuilder(passwordAuthentication,
                registeredClient, usernamePasswordAuthentication, authorizedScopes);

        OAuth2Authorization.Builder authorizationBuilder = authorizationBuilder(registeredClient,
                usernamePasswordAuthentication, authorizedScopes);

        OAuth2AccessToken accessToken = genAccessToken(tokenContextBuilder, authorizationBuilder);

        OAuth2RefreshToken refreshToken = genRefreshToken(clientPrincipal, registeredClient, tokenContextBuilder,
                authorizationBuilder);

        this.authorizationService.save(authorizationBuilder.build());

        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient, clientPrincipal, accessToken, refreshToken, Collections.emptyMap());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 用户名密码认证
     * @param passwordAuthentication /
     * @return /
     */
    private Authentication userNamePasswordAuthenticate(OAuth2PasswordAuthenticationToken passwordAuthentication) {
        Map<String, Object> reqParameters = passwordAuthentication.getAdditionalParameters();
        String username = (String) reqParameters.get(OAuth2ParameterNames.USERNAME);
        String password = (String) reqParameters.get(OAuth2ParameterNames.PASSWORD);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication usernamePasswordAuthentication;
        try{
            usernamePasswordAuthentication = authenticationManager.authenticate(token);
        }catch (Exception e){
            throw oAuth2AuthenticationException(e);
        }
        return usernamePasswordAuthentication;
    }

    private DefaultOAuth2TokenContext.Builder tokenContextBuilder(OAuth2PasswordAuthenticationToken passwordAuthentication,
                                                                  RegisteredClient registeredClient,
                                                                  Authentication usernamePasswordAuthentication,
                                                                  Set<String> authorizedScopes) {
        // @formatter:off
        return DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthentication)
                .providerContext(ProviderContextHolder.getProviderContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrant(passwordAuthentication);
        // @formatter:on
    }

    private OAuth2Authorization.Builder authorizationBuilder(RegisteredClient registeredClient,
                                                             Authentication usernamePasswordAuthentication,
                                                             Set<String> authorizedScopes) {
        return OAuth2Authorization
                .withRegisteredClient(registeredClient)
                .id(UUID.randomUUID().toString())
                .principalName(usernamePasswordAuthentication.getName())
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .attribute(Principal.class.getName(), usernamePasswordAuthentication)
                .attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, authorizedScopes);
    }

    private OAuth2AccessToken genAccessToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder) {
        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }
        return accessToken;
    }

    private OAuth2RefreshToken genRefreshToken(OAuth2ClientAuthenticationToken clientPrincipal, RegisteredClient registeredClient, DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder) {
        OAuth2TokenContext tokenContext;
        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }
        return refreshToken;
    }

    /**
     * OAuth2 认证失败处理器只能处理 OAuth2AuthenticationException，故转换
     *
     * @param authenticationException 身份验证异常
     * @return {@link OAuth2AuthenticationException}
     */
    private OAuth2AuthenticationException oAuth2AuthenticationException(Exception authenticationException) {
        if (authenticationException instanceof UsernameNotFoundException) {
            return new PiOAuth2AuthencticationException(ResponseStatus.ACCOUNT_NOT_EXIST);
        }
        if (authenticationException instanceof BadCredentialsException) {
            return new PiOAuth2AuthencticationException(ResponseStatus.USERNAME_OR_PASSWORD_INCORRECT);
        }
        if (authenticationException instanceof LockedException) {
            return new PiOAuth2AuthencticationException(ResponseStatus.ACCOUNT_FROZEN);
        }
        if (authenticationException instanceof AccountExpiredException) {
            return new PiOAuth2AuthencticationException(ResponseStatus.ACCOUNT_EXPIRED);
        }
        if (authenticationException instanceof CredentialsExpiredException) {
            return new PiOAuth2AuthencticationException(ResponseStatus.PASSWORD_EXPIRED);
        }
        if( authenticationException instanceof InternalAuthenticationServiceException){
            if(authenticationException.getCause() instanceof DisabledException){
                return new PiOAuth2AuthencticationException(ResponseStatus.ACCOUNT_DISABLE);
            }
        }
        return new PiOAuth2AuthencticationException(ResponseStatus.IDENTITY_VERIFICATION_FAILED);
    }
}