package me.pi.cloud.common.feigin.config;

import me.pi.cloud.common.feigin.intercepter.AutoAuthorizationRequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author ZnPi
 * @date 2022-08-15
 */
@AutoConfiguration
public class FeignAutoConfiguration {
    @Bean
    public AutoAuthorizationRequestInterceptor autoAuthorizationRequestInterceptor() {
        return new AutoAuthorizationRequestInterceptor();
    }
}
