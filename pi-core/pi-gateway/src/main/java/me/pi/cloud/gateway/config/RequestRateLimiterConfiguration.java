package me.pi.cloud.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author ZnPi
 * @date 2023/01/18
 */
@Configuration
public class RequestRateLimiterConfiguration {
    @Bean
    @Primary
    KeyResolver hostAddressResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress())
                .getAddress().getHostAddress());
    }
}
