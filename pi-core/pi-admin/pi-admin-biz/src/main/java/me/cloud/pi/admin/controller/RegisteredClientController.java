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

package me.cloud.pi.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.pojo.dto.RegisteredClientDTO;
import me.cloud.pi.admin.service.RegisteredClientService;
import me.cloud.pi.common.web.pojo.query.BaseQueryParam;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * @author ZnPi
 * @date 2022-11-14
 */
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@Tag(name = "RegisteredClientController", description = "客户端管理")
public class RegisteredClientController {
    private final RegisteredClientService registeredClientService;

    @Operation(summary = "获取客户端")
    @GetMapping
    public ResponseData<?> getClients(BaseQueryParam queryParam) {
        return ResponseData.ok(registeredClientService.getClients(queryParam));
    }

    @Operation(summary = "新增或编辑客户端")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseData<?> saveOrUpdate(@RequestBody RegisteredClientDTO dto) {
        registeredClientService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除客户端")
    public ResponseData<?> delClient(String ids){
        registeredClientService.delClient(ids);
        return ResponseData.ok();
    }
}
