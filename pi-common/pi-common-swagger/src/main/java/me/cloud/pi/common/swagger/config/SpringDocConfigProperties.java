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
