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
