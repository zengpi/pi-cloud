package me.cloud.pi.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author lw
 * @date 2022-11-11
 */
@Data
@ConfigurationProperties(prefix = SpringDocConfigProperties.PREFIX)
public class SpringDocConfigProperties {
        /** Prefix of {@link SpringDocConfigProperties}. */
        public static final String PREFIX = "springdoc";

        /**服务列表*/
        private Map<String, String> servers;
}
