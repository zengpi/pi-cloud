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

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import me.cloud.pi.admin.pojo.dto.MenuDTO;
import me.cloud.pi.admin.pojo.po.SysMenu;
import me.cloud.pi.admin.pojo.query.MenuQueryParam;
import me.cloud.pi.admin.pojo.vo.MenuVO;
import me.cloud.pi.common.web.pojo.vo.SelectTreeVO;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
public interface MenuService extends IService<SysMenu> {
    /**
     * 根据角色 ID 获取权限标识
     * @param ids 角色 ID
     * @return 权限标识
     */
    List<SysMenu> listPermissionByRoleIds(Long[] ids);

    /**
     * 构建当前用户的树形菜单
     * @return 当前用户的树形菜单
     */
    List<Tree<Long>> buildMenu();

    /**
     * 获取菜单
     * @param query 查询条件
     * @return 菜单列表
     */
    List<MenuVO> getMenus(MenuQueryParam query);

    /**
     * 菜单树形选择
     * @param containBtn 是否包含按钮
     * @return /
     */
    List<SelectTreeVO> selectTree(boolean containBtn);

    /**
     * 新增或编辑
     * @param dto 菜单表单 DTO
     */
    void saveOrUpdate(MenuDTO dto);

    /**
     * 删除
     * @param ids 菜单 ID，多个以逗号分隔
     */
    void delete(String ids);
}
