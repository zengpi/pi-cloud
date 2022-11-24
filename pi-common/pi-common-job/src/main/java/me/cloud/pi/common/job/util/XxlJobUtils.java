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

package me.cloud.pi.common.job.util;

import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import me.cloud.pi.common.job.config.XxlJobExecutorProperties;
import org.springframework.core.env.Environment;

/**
 * @author ZnPi
 * @date 2022-11-24
 */
public class XxlJobUtils {
    public static XxlJobSpringExecutor getXxlJobSpringExecutor(XxlJobExecutorProperties xxlJobExecutorProperties,
                                                         String adminAddress,
                                                         Environment environment) {
        String appname = xxlJobExecutorProperties.getAppname();

        if (StrUtil.isBlank(appname)) {
            appname = environment.getProperty("spring.application.name");
        }

        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddress);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAddress(xxlJobExecutorProperties.getAddress());
        xxlJobSpringExecutor.setIp(xxlJobExecutorProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobExecutorProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobExecutorProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobExecutorProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobExecutorProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
