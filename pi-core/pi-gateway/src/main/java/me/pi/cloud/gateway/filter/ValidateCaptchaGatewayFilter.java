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

package me.pi.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.pi.cloud.common.redis.constant.CacheConstants;
import me.pi.cloud.gateway.enums.ResponseStatusEnum;
import me.pi.cloud.gateway.exception.CaptchaEmptyException;
import me.pi.cloud.gateway.exception.CaptchaIncorrectException;
import me.pi.cloud.gateway.util.ResponseData;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 校验验证码过滤器
 *
 * @author ZnPi
 * @date 2022-08-24
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ValidateCaptchaGatewayFilter extends AbstractGatewayFilterFactory<Object> {
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Token URL 标识
     */
    private static final String TOKEN_URL_IDENTIFY = "oauth2/token";

    /**
     * 授权类型
     */
    private static final String GRANT_TYPE = "grant_type";

    /**
     * password 授权类型
     */
    private static final String PASSWORD = "password";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            // 非获取 token 请求直接放行
            String path = request.getURI().getPath();
            if (!StrUtil.containsIgnoreCase(path, TOKEN_URL_IDENTIFY)) {
                return chain.filter(exchange);
            }
            // 非密码模式直接放行
            String grantType = request.getQueryParams().getFirst(GRANT_TYPE);
            if (!PASSWORD.equals(grantType)) {
                return chain.filter(exchange);
            }

            try {
                // 校验验证码
                String code = request.getQueryParams().getFirst("code");
                if (StrUtil.isBlank(code)) {
                    throw new CaptchaEmptyException(ResponseStatusEnum.CAPTCHA_EMPTY);
                }

                String randomCode = request.getQueryParams().getFirst("randomCode");

                String captchaKey = CacheConstants.CAPTCHA_PREFIX + randomCode;
                String captcha = stringRedisTemplate.opsForValue().get(captchaKey);

                stringRedisTemplate.delete(captchaKey);

                if (StrUtil.isBlank(captcha) || !code.equals(captcha)) {
                    throw new CaptchaIncorrectException(ResponseStatusEnum.CAPTCHA_INCORRECT);
                }
            } catch (CaptchaEmptyException e) {
                return getErrResponse(exchange, ResponseData.error(ResponseStatusEnum.CAPTCHA_EMPTY));
            } catch (CaptchaIncorrectException e) {
                return getErrResponse(exchange, ResponseData.error(ResponseStatusEnum.CAPTCHA_INCORRECT));
            } catch (Exception e) {
                return getErrResponse(exchange, ResponseData.error(ResponseStatusEnum.SYS_ERROR, e.getMessage()));
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> getErrResponse(ServerWebExchange exchange, Object errInfo) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.create(monoSink -> {
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(errInfo);
                DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                monoSink.success(dataBuffer);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error(jsonProcessingException.getMessage());
                monoSink.error(jsonProcessingException);
            }
        }));
    }
}
