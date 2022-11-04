package me.cloud.pi.auth.extension;

import me.cloud.pi.auth.constant.SecurityConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author ZnPi
 * @date 2022-10-22
 */
@Component
public class PiOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    @Override
    public void customize(JwtEncodingContext context) {
        JwtClaimsSet.Builder builder = context.getClaims();

        // 客户端模式不返回具体用户信息
        if (SecurityConstants.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
            return;
        }

        User user = (User) context.getPrincipal().getPrincipal();
        builder.claims((claims) -> {
            claims.put("username", user.getUsername());
            claims.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray());
        });
    }
}