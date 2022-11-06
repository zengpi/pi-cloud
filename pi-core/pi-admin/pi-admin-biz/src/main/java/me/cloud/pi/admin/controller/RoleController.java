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

import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.pojo.dto.AllocationRoleMenuDTO;
import me.cloud.pi.admin.pojo.dto.AllocationRoleUserDTO;
import me.cloud.pi.admin.pojo.dto.RoleDTO;
import me.cloud.pi.admin.pojo.query.RoleMemberQueryParam;
import me.cloud.pi.admin.pojo.query.RoleQueryParam;
import me.cloud.pi.admin.pojo.vo.RoleMemberVO;
import me.cloud.pi.admin.pojo.vo.RoleVO;
import me.cloud.pi.admin.service.RoleMenuService;
import me.cloud.pi.admin.service.RoleService;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMenuService roleMenuService;

    /**
     * 角色查询
     *
     * @param queryParam 查询参数
     * @return 角色列表
     */
    @GetMapping
    public ResponseData<PiPage<RoleVO>> roles(RoleQueryParam queryParam) {
        return ResponseData.ok(roleService.roles(queryParam));
    }

    /**
     * 角色新增
     *
     * @param dto /
     * @return /
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
    public ResponseData<?> saveOrUpdate(@RequestBody @Valid RoleDTO dto) {
        roleService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    /**
     * 角色删除
     * @param ids 待删除 ID，多个以逗号分隔
     * @return /
     */
    @DeleteMapping
    public ResponseData<?> del(String ids){
        roleService.del(ids);
        return ResponseData.ok();
    }

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    @GetMapping("/allRoles")
    public ResponseData<List<RoleVO>> allRoles() {
        return ResponseData.ok(roleService.getAllRoles());
    }

    /**
     * 角色成员
     * @param queryParam 查询参数
     * @return 角色成员列表
     */
    @GetMapping("/roleMembers")
    public ResponseData<PiPage<RoleMemberVO>> getRoleMembers(@Valid RoleMemberQueryParam queryParam){
        return ResponseData.ok(roleService.getRoleMembers(queryParam));
    }

    /**
     * 为角色分配用户
     * @param dto /
     * @return /
     */
    @PostMapping("/allocationRoleUser")
    public ResponseData<?> allocationRoleUser(@RequestBody AllocationRoleUserDTO dto){
        roleService.allocationRoleUser(dto);
        return ResponseData.ok();
    }

    /**
     * 为角色分配菜单
     * @param dto /
     * @return /
     */
    @PostMapping("/allocationRoleMenu")
    public ResponseData<?> allocateRoleMenu(@RequestBody @Valid AllocationRoleMenuDTO dto){
        roleMenuService.allocationRoleMenu(dto);
        return ResponseData.ok();
    }
}
