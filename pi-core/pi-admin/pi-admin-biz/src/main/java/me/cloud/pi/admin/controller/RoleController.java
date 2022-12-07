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
import me.cloud.pi.admin.pojo.dto.RoleDTO;
import me.cloud.pi.admin.pojo.dto.RoleMenuAllocationDTO;
import me.cloud.pi.admin.pojo.dto.RoleUserAllocationDTO;
import me.cloud.pi.admin.pojo.query.RoleMemberQuery;
import me.cloud.pi.admin.pojo.vo.RoleMemberVO;
import me.cloud.pi.admin.pojo.vo.RoleVO;
import me.cloud.pi.admin.service.RoleMenuService;
import me.cloud.pi.admin.service.RoleService;
import me.cloud.pi.admin.service.UserRoleService;
import me.cloud.pi.admin.service.UserService;
import me.cloud.pi.common.mybatis.base.BaseQuery;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色管理")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;
    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;

    @GetMapping
    @Operation(summary = "获取角色")
    @PreAuthorize("hasAuthority('sys_role_query')")
    public ResponseData<PiPage<RoleVO>> getRoles(BaseQuery query) {
        return ResponseData.ok(roleService.getRoles(query));
    }

    @PostMapping
    @Operation(summary = "新增角色")
    @PreAuthorize("hasAuthority('sys_role_add')")
    public ResponseData<?> saveRole(@RequestBody @Valid RoleDTO dto) {
        roleService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    @PutMapping
    @Operation(summary = "编辑角色")
    @PreAuthorize("hasAuthority('sys_role_edit')")
    public ResponseData<?> updateRole(@RequestBody @Valid RoleDTO dto) {
        roleService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除角色")
    @PreAuthorize("hasAuthority('sys_role_delete')")
    public ResponseData<?> deleteRole(@PathVariable String ids){
        roleService.deleteRole(ids);
        return ResponseData.ok();
    }

    @GetMapping("/allRoles")
    @Operation(summary = "获取所有角色")
    public ResponseData<List<RoleVO>> allRoles() {
        return ResponseData.ok(roleService.getAllRoles());
    }

    @GetMapping("/roleMembers")
    @Operation(summary = "获取角色成员")
    public ResponseData<PiPage<RoleMemberVO>> getRoleMembers(@Valid RoleMemberQuery queryParam){
        return ResponseData.ok(userService.getRoleMembers(queryParam));
    }

    @PostMapping("/roleUserAllocation")
    @Operation(summary = "角色用户分配")
    @PreAuthorize("hasAuthority('sys_role_user_allocation')")
    public ResponseData<?> allocateRoleUser(@RequestBody RoleUserAllocationDTO dto){
        userRoleService.allocationRoleUser(dto);
        return ResponseData.ok();
    }

    @PostMapping("/roleMenuAllocation")
    @Operation(summary = "角色菜单分配")
    @PreAuthorize("hasAuthority('sys_role_menu_allocation')")
    public ResponseData<?> allocateRoleMenu(@RequestBody @Valid RoleMenuAllocationDTO dto){
        roleMenuService.roleMenuAllocation(dto);
        return ResponseData.ok();
    }
}
