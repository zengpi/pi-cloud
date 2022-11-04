package me.cloud.pi.auth.extension.password;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
public class OAuth2PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    private final Set<String> scopes;

    /**
     * Sub-class constructor.
     *
     * @param clientPrincipal        the authenticated client principal
     * @param additionalParameters   the additional parameters
     */
    protected OAuth2PasswordAuthenticationToken(Authentication clientPrincipal,
                                                @Nullable Set<String> scopes,
                                                Map<String, Object> additionalParameters) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet(
                scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
    }

    /**
     * Returns the requested scope(s).
     *
     * @return the requested scope(s), or an empty {@code Set} if not available
     */
    public Set<String> getScopes() {
        return this.scopes;
    }
}