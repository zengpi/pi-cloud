package me.pi.cloud.common.web.config;

import me.pi.cloud.common.web.util.HttpEndpointUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author ZnPi
 * @date 2023-01-13 16:40
 */
@AutoConfiguration
public class WebAutoConfiguration {
    @Bean
    public HttpEndpointUtils httpEndpointUtils(){
        return new HttpEndpointUtils();
    }
}
