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