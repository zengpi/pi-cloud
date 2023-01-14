package me.pi.cloud.auth.extension.password;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * An Authentication implementation used for the OAuth 2 Password Grant.
 *
 * @author ZnPi
 * @date 2022-10-21
 */
public class OAuth2PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    @Getter
    private final String username;
    @Getter
    private final String password;

    /**
     * Constructs an {@code OAuth2PasswordAuthenticationToken} using the provided parameters.
     *
     * @param username             the username
     * @param password             the password
     * @param clientPrincipal      the authenticated client principal
     * @param additionalParameters the additional parameters
     */
    protected OAuth2PasswordAuthenticationToken(String username, String password, Authentication clientPrincipal,
                                                Map<String, Object> additionalParameters) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        Assert.hasText(username, "username cannot be empty");
        Assert.hasText(password, "password cannot be empty");
        this.username = username;
        this.password = password;
    }
}
