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

package me.pi.cloud.common.log.config;

import me.pi.cloud.admin.api.feign.FeignLogService;
import me.pi.cloud.common.log.aspect.LogAspect;
import me.pi.cloud.common.log.listener.LogListener;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author ZnPi
 * @date 2022-11-18
 */
@AutoConfiguration
@EnableAsync
public class LogAutoConfiguration {
    @Bean
    public LogListener logListener(FeignLogService logService) {
        return new LogListener(logService);
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
