package me.pi.cloud.auth.extension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.pi.cloud.auth.constant.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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