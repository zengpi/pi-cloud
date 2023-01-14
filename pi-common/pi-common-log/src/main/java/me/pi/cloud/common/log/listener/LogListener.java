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

package me.pi.cloud.common.log.listener;

import lombok.RequiredArgsConstructor;
import me.pi.cloud.admin.api.dto.LogDTO;
import me.pi.cloud.admin.api.feign.FeignLogService;
import me.pi.cloud.common.feigin.util.AccessTokenHolder;
import me.pi.cloud.common.log.event.LogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @author ZnPi
 * @date 2022-11-18
 */
@RequiredArgsConstructor
public class LogListener {
    private final FeignLogService feignLogService;

    @Async
    @EventListener(LogEvent.class)
    public void processLogEvent(LogEvent event) {
        try {
            LogDTO logDTO = (LogDTO) event.getSource();
            String token = event.getToken();
            AccessTokenHolder.setToken(token);
            feignLogService.saveLog(logDTO);
        } catch (Exception e) {
            AccessTokenHolder.remove();
            throw new RuntimeException(e);
        }
    }
}
