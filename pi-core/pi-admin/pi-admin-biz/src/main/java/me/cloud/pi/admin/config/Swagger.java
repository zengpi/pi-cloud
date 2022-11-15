package me.cloud.pi.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lw
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class Swagger {
        /**ip*/
        private String ip = "localhost";
        /**标题*/
        private String title = "swagger管理界面";
        /**描述*/
        private String description = "管理所有swagger服务";
        /**版本号*/
        private String version = "1.0";
}
