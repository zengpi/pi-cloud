package me.cloud.pi.common.swagger.config;

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

        /**标题*/
        private String title = "PI-CLOUD API";

        /**描述*/
        private String description = "什么都没写...";

        /**版本号*/
        private String version;

        /**服务网关，即 Servers 的地址*/
        private String serverGateway;

        /**token-url*/
        private String tokenUrl;

        /**服务列表*/
        private Map<String, String> servers;
}
