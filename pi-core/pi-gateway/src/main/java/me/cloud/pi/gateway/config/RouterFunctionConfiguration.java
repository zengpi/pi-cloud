package me.cloud.pi.gateway.config;

import lombok.RequiredArgsConstructor;
import me.cloud.pi.gateway.handler.CaptchaHandler;
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