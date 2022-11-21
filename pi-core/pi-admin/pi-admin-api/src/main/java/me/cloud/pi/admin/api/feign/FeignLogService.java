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

package me.cloud.pi.admin.api.feign;

import me.cloud.pi.admin.api.dto.LogDTO;
import me.cloud.pi.common.web.constant.ServiceNameConstants;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ZnPi
 * @date 2022-11-18
 */
@FeignClient(value = ServiceNameConstants.PI_ADMIN)
public interface FeignLogService {
    /**
     * 保存日志
     *
     * @param dto /
     * @return /
     */
    @PostMapping("/log")
    ResponseData<?> saveLog(@RequestBody LogDTO dto);
}
