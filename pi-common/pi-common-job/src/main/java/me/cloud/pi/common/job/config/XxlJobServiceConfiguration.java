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

import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.cloud.pi.common.job.util.XxlJobUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.stream.Collectors;

/**
 * xxl-job config。如果你将微服务作为执行器，则自动使用此配置
 * @author ZnPi
 * @date 2022-11-23
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({XxlJobExecutorProperties.class, XxlJobAdminProperties.class})
@ConditionalOnClass(DiscoveryClient.class)
public class XxlJobServiceConfiguration {
    private static final String ADMIN_CONTEXT_PTH = "/xxl-job-admin";
    private final XxlJobAdminProperties xxlJobAdminProperties;
    private final XxlJobExecutorProperties xxlJobExecutorProperties;
    private final DiscoveryClient discoveryClient;
    private final Environment environment;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");

        String adminAddresses = xxlJobAdminProperties.getAddresses();
        if(StrUtil.isBlank(adminAddresses) && discoveryClient != null){
            String adminServiceName = xxlJobAdminProperties.getServiceName();
            adminAddresses = discoveryClient.getInstances(adminServiceName).stream()
                    .map(instance ->
                            String.format("http://%s:%s%s", instance.getHost(), instance.getPort(), ADMIN_CONTEXT_PTH))
                    .collect(Collectors.joining(","));
        }

        return XxlJobUtils.getXxlJobSpringExecutor(xxlJobExecutorProperties, adminAddresses, environment);
    }
}