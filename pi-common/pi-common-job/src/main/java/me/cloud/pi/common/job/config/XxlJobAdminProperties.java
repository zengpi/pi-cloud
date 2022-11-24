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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZnPi
 * @date 2022-11-24
 */
@Data
@ConfigurationProperties(prefix = "xxl.job.admin")
public class XxlJobAdminProperties {
    /**
     * 调度中心部署根地址 [选填]；
     * 如调度中心集群部署存在多个地址则用逗号分隔；
     * 执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；
     * 为空则关闭自动注册。
     */
    private String addresses;

    /**
     * 调度中心部署微服务名称 [选填];
     * 默认：pi-job
     */
    private String serviceName = "pi-job";
}
