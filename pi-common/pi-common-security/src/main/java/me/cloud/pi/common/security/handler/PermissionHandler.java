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

package me.cloud.pi.common.security.handler;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import me.cloud.pi.common.security.constant.SecurityConstants;
import me.cloud.pi.common.security.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class PermissionHandler {
	public boolean hasPermission(String... permissions) {
		if (ArrayUtil.isEmpty(permissions)) {
			return false;
		}
		Authentication authentication = SecurityUtils.getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.map(e -> StrUtil.removePrefix(e, SecurityConstants.ROLE))
				.filter(StringUtils::hasText)
				.anyMatch(e -> PatternMatchUtils.simpleMatch(permissions, e));
	}
}