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

package me.pi.cloud.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.pi.cloud.admin.pojo.dto.RegisteredClientDTO;
import me.pi.cloud.admin.pojo.vo.RegisteredClientVO;
import me.pi.cloud.admin.service.RegisteredClientService;
import me.pi.cloud.common.mybatis.base.BaseQuery;
import me.pi.cloud.common.mybatis.util.PiPage;
import me.pi.cloud.common.web.util.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZnPi
 * @date 2022-11-14
 */
@RestController
@RequestMapping("/client")
@Tag(name = "客户端管理")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class RegisteredClientController {
    private final RegisteredClientService registeredClientService;

    @GetMapping
    @Operation(summary = "获取客户端")
    @PreAuthorize("hasAuthority('sys_user_query')")
    public ResponseData<PiPage<RegisteredClientVO>> getClients(BaseQuery query) {
        return ResponseData.ok(registeredClientService.getClients(query));
    }

    @PostMapping
    @Operation(summary = "新增客户端")
    @PreAuthorize("hasAuthority('sys_user_add')")
    public ResponseData<?> saveClient(@RequestBody RegisteredClientDTO dto) {
        registeredClientService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    @PutMapping
    @Operation(summary = "编辑客户端")
    @PreAuthorize("hasAuthority('sys_user_edit')")
    public ResponseData<?> updateClient(@RequestBody RegisteredClientDTO dto) {
        registeredClientService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除客户端")
    @PreAuthorize("hasAuthority('sys_user_delete')")
    public ResponseData<?> delClient(@PathVariable String ids){
        registeredClientService.delClient(ids);
        return ResponseData.ok();
    }
}
