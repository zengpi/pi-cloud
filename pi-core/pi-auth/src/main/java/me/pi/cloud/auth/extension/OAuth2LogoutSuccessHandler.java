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

package me.pi.cloud.auth.extension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import me.pi.cloud.common.web.util.HttpEndpointUtils;
import me.pi.cloud.common.web.util.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

/**
 * @author ZnPi
 * @date 2022-10-22
 */
public class OAuth2LogoutSuccessHandler implements LogoutSuccessHandler {
    private final HttpStatus httpStatusToReturn;

    public OAuth2LogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        Assert.notNull(httpStatusToReturn, "The provided HttpStatus must not be null.");
        this.httpStatusToReturn = httpStatusToReturn;
    }

    public OAuth2LogoutSuccessHandler() {
        this.httpStatusToReturn = HttpStatus.OK;
    }

    @Override
    @SneakyThrows
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setStatus(this.httpStatusToReturn.value());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        HttpEndpointUtils.writeWithMessageConverters(ResponseData.ok(), httpResponse);
    }
}