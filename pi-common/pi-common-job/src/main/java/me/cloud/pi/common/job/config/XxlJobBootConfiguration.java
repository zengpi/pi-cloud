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

package me.cloud.pi.common.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.cloud.pi.common.job.util.XxlJobUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * xxl-job config。如果您使用普通的 Spring Boot 作为执行器，则自动使用此配置
 * @author ZnPi
 * @date 2022-11-24
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({XxlJobExecutorProperties.class, XxlJobAdminProperties.class})
@ConditionalOnMissingClass("org.springframework.cloud.client.discovery.DiscoveryClient")
public class XxlJobBootConfiguration {
    private final XxlJobAdminProperties xxlJobAdminProperties;
    private final XxlJobExecutorProperties xxlJobExecutorProperties;
    private final Environment environment;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        return XxlJobUtils.getXxlJobSpringExecutor(xxlJobExecutorProperties,
                xxlJobAdminProperties.getAddresses(), environment);
    }
}
