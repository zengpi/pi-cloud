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

package me.cloud.pi.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.cloud.pi.admin.pojo.dto.MenuDTO;
import me.cloud.pi.admin.pojo.po.SysMenu;
import me.cloud.pi.admin.pojo.query.MenuTreeQuery;
import me.cloud.pi.admin.pojo.vo.CurrentUserMenuTreeVO;
import me.cloud.pi.admin.pojo.vo.MenuTreeVO;
import me.cloud.pi.common.web.pojo.vo.SelectTreeVO;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
public interface MenuService extends IService<SysMenu> {
    /**
     * 获取菜单（树形）
     * @param query 查询条件
     * @return 菜单（树形）
     */
    List<MenuTreeVO> getMenuTree(MenuTreeQuery query);

    /**
     * 新增或编辑菜单
     * @param dto MenuDTO
     */
    void saveOrUpdate(MenuDTO dto);

    /**
     * 删除
     * @param ids 菜单 ID，多个以逗号分隔
     */
    void deleteMenu(String ids);

    /**
     * 构建当前用户的树形菜单
     * @param username 用户名，用于缓存 key
     * @return 当前用户的树形菜单
     */
    List<CurrentUserMenuTreeVO> buildMenu(String username);

    /**
     * 获取菜单选择器（树形）
     * @param containsButtons 是否包含按钮
     * @return 菜单选择器（树形）
     */
    List<SelectTreeVO> getMenuSelectTree(Boolean containsButtons);

    /**
     * 根据角色 ID 获取权限标识
     * @param ids 角色 ID
     * @return 权限标识
     */
    List<SysMenu> listPermissionByRoleIds(Long[] ids);

    /**
     * 根据角色 ID 获取菜单 ID 列表
     *
     * @param roleId 角色 ID
     * @return 菜单 ID 列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);
}
