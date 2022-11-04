package me.cloud.pi.gateway.handler;

import com.pig4cloud.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.common.redis.constant.CacheConstants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author ZnPi
 * @date 2022-08-24
 */
@Component
@RequiredArgsConstructor
public class CaptchaHandler {
    private static final Integer DEFAULT_IMAGE_WIDTH = 100;

    private static final Integer DEFAULT_IMAGE_HEIGHT = 32;

    private final StringRedisTemplate stringRedisTemplate;

    @NonNull
    public Mono<ServerResponse> captcha(ServerRequest request)  {
        String randomCode = request.pathVariable("randomCode");

        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
        String captcha = arithmeticCaptcha.text();

        stringRedisTemplate.opsForValue().set(CacheConstants.CAPTCHA_PREFIX + randomCode, captcha,
                CacheConstants.CAPTCHA_TIME_OUT, TimeUnit.SECONDS);

        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        arithmeticCaptcha.out(out);

        return ok().contentType(IMAGE_JPEG).body(BodyInserters.fromResource(new ByteArrayResource(out.toByteArray())));
    }
}
