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

package me.pi.cloud.common.log.util;

import jakarta.servlet.http.HttpServletRequest;
import me.pi.cloud.admin.api.dto.LogDTO;
import me.pi.cloud.common.util.ServletUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * @author ZnPi
 * @date 2022-11-20
 */
public class LogUtils {
    public static LogDTO getDefaultLogDTO(){
        LogDTO logDTO = new LogDTO();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Optional.ofNullable(requestAttributes).ifPresent(attributes -> {
            HttpServletRequest request = attributes.getRequest();
            logDTO.setIp(ServletUtil.getClientIP(request));
            logDTO.setRequestMethod(request.getMethod());
        });

        return logDTO;
    }

    public static String getToken(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            HttpServletRequest request = requestAttributes.getRequest();
            return request.getHeader(HttpHeaders.AUTHORIZATION);
        }
        return null;
    }
}
