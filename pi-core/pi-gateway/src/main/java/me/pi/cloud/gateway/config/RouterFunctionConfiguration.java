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

package me.pi.cloud.gateway.config;

import lombok.RequiredArgsConstructor;
import me.pi.cloud.gateway.handler.CaptchaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author ZnPi
 * @date 2022-08-24
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RouterFunctionConfiguration {
    private final CaptchaHandler captchaHandler;

    /**
     * 获取验证码
     * @return 验证码
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route().GET("/captcha/{randomCode}", captchaHandler::captcha).build();
    }
}