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

package me.cloud.pi.auth.extension;

import lombok.RequiredArgsConstructor;
import me.cloud.pi.auth.constant.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZnPi
 * @date 2022-10-22
 */
@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler implements LogoutHandler {
    private final OAuth2AuthorizationService authorizationService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION);
        if(!StringUtils.hasText(authHeader)){
            return;
        }
        String token = authHeader.replaceAll(SecurityConstants.TOKEN_PREFIX + " *", "");
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if(authorization == null){
            return;
        }

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if(accessToken == null || !StringUtils.hasText(accessToken.getToken().getTokenValue())){
            return;
        }

        authorizationService.remove(authorization);
    }
}