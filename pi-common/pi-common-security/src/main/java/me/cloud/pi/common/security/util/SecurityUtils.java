package me.cloud.pi.common.security.util;

import cn.hutool.core.util.StrUtil;
import me.cloud.pi.common.security.constant.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-10-23
 */
public class SecurityUtils {
    /**
     * 获取 Authentication
     *
     * @return Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Jwt getJwt() {
        Authentication authentication = SecurityUtils.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt) {
            return (Jwt) principal;
        }
        return null;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public static String getUserName() {
        Jwt jwt = getJwt();
        if (jwt == null) {
            return null;
        }
        return jwt.getClaimAsString("username");
    }

    /**
     * 获取当前用户的授权
     * @return 当前用户的授权
     */
    public static List<String> getRoleCodeList(){
        Jwt jwt = getJwt();
        if(jwt == null) {
            return Collections.emptyList();
        }

        return jwt.getClaimAsStringList("authorities")
                .stream()
                .map(e -> StrUtil.removePrefix(e, SecurityConstants.ROLE))
                .collect(Collectors.toList());
    }
}
