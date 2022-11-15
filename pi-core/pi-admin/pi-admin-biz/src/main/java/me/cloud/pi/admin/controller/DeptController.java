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
import me.cloud.pi.admin.pojo.vo.DeptTreeVO;
import me.cloud.pi.admin.service.DeptService;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@RestController
@RequestMapping("dept")
@RequiredArgsConstructor
@Tag(name = "DeptController", description = "部门管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DeptController {
    private final DeptService deptService;

    /**
     * 返回部门的树形菜单
     *
     * @return 当前用户的树形菜单
     */
    @Operation(summary = "返回部门的树形菜单")
    @GetMapping("/deptTree")
    public ResponseData<List<DeptTreeVO>> deptTree(){
        return ResponseData.ok(deptService.getDeptTree());
    }
}
