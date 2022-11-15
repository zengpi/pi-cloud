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

import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.pojo.dto.MenuDTO;
import me.cloud.pi.admin.pojo.query.MenuQueryParam;
import me.cloud.pi.admin.pojo.vo.MenuVO;
import me.cloud.pi.admin.service.MenuService;
import me.cloud.pi.admin.service.RoleMenuService;
import me.cloud.pi.common.web.pojo.vo.SelectTreeVO;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Tag(name = "MenuController", description = "菜单管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MenuController {
    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    /**
     * 获取菜单
     *
     * @return 菜单列表
     */
    @Operation(summary = "获取所有菜单列表")
    @GetMapping
    public ResponseData<List<MenuVO>> getMenus(MenuQueryParam query) {
        return ResponseData.ok(menuService.getMenus(query));
    }

    /**
     * 菜单树形选择
     *
     * @param containBtn 是否包含按钮
     * @return /
     */
    @Operation(summary = "菜单树形选择")
    @GetMapping("/selectTree")
    public ResponseData<List<SelectTreeVO>> selectTree(boolean containBtn) {
        return ResponseData.ok(menuService.selectTree(containBtn));
    }

    /**
     * 返回当前用户的树形菜单
     *
     * @return 当前用户的树形菜单
     */
    @Operation(summary = "返回当前用户的树形菜单")
    @GetMapping("/buildMenu")
    public ResponseData<List<Tree<Long>>> buildMenu() {
        return ResponseData.ok(menuService.buildMenu());
    }

    /**
     * 新增或编辑
     *
     * @param dto 菜单表单 DTO
     * @return /
     */
    @Operation(summary = "新增或编辑")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseData<?> saveOrUpdate(@RequestBody MenuDTO dto) {
        menuService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    /**
     * 删除
     * @param ids 菜单 ID，多个以逗号分隔
     * @return /
     */
    @Operation(summary = "删除")
    @DeleteMapping
    public ResponseData<?> delete(String ids){
        menuService.delete(ids);
        return ResponseData.ok();
    }

    /**
     * 根据角色 ID 获取菜单 ID 列表
     * @param roleId 角色 ID
     * @return 菜单 ID 列表
     */
    @Operation(summary = "根据角色 ID 获取菜单 ID 列表")
    @GetMapping("/menuLeafIds/{roleId}")
    public ResponseData<List<Long>> getMenuLeafIdsByRoleId(@PathVariable Long roleId){
        return ResponseData.ok(roleMenuService.getMenuLeafIdsByRoleId(roleId));
    }
}
