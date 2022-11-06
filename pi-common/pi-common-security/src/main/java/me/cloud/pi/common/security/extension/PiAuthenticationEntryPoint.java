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

package me.cloud.pi.common.security.extension;

import lombok.SneakyThrows;
import me.cloud.pi.common.web.enums.ResponseStatus;
import me.cloud.pi.common.web.util.HttpEndpointUtils;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZnPi
 * @date 2022-08-17
 */
public class PiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ServletServerHttpResponse servletServerHttpResponse = new ServletServerHttpResponse(response);
        ResponseData<?> error;
        if(authException instanceof InvalidBearerTokenException){
            servletServerHttpResponse.setStatusCode(HttpStatus.FAILED_DEPENDENCY);
            error = ResponseData.error(ResponseStatus.INVALID_GRANT, authException.getMessage());
        }else{
            servletServerHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            error = ResponseData.error(ResponseStatus.UNAUTHORIZED, authException.getMessage());
        }

        HttpEndpointUtils.writeWithMessageConverters(error, servletServerHttpResponse);
    }
}
