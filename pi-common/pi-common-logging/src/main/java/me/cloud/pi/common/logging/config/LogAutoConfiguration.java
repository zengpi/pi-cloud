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

package me.cloud.pi.common.logging.config;

import me.cloud.pi.admin.api.feign.FeignLogService;
import me.cloud.pi.common.logging.aspect.LogAspect;
import me.cloud.pi.common.logging.event.LogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author ZnPi
 * @date 2022-11-18
 */
@Configuration
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
