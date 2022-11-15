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

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;

/**
 * @author lw
 * @date 2022-11-15
 */
@Data
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SpringDocConfigProperties.class)
public class SpringDocAutoConfiguration {
    private SpringDocConfigProperties springDocConfigProperties;

    private ServiceInstance serviceInstance;

    @Autowired
    private void setSpringDocConfigProperties(SpringDocConfigProperties springDocConfigProperties){
        this.springDocConfigProperties = springDocConfigProperties;
    }

    @Autowired(required = false)
    public void setServiceInstance(ServiceInstance serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    @Bean
    public OpenAPI openApi() {
        ArrayList<Server> servers = new ArrayList<>();
        if (serviceInstance != null && springDocConfigProperties.getServers() != null) {
            String server = springDocConfigProperties.getServers().get(serviceInstance.getServiceId());
            if (StrUtil.isNotBlank(server)) {
                servers.add(new Server().url(springDocConfigProperties.getServerGateway() + "/" + server));
            }
        }

        return new OpenAPI()
                .info(new Info()
                        .title(springDocConfigProperties.getTitle())
                        .description(springDocConfigProperties.getDescription())
                        .version(springDocConfigProperties.getVersion())
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Pi Cloud 参考文档")
                        .url("https://www.yuque.com/zengpi/szfuh0"))
                .components(new Components()
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .password(new OAuthFlow()
                                                .tokenUrl(springDocConfigProperties.getTokenUrl())))))
                .servers(servers);
    }
}