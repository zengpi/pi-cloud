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

package me.pi.cloud.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.pi.cloud.admin.converter.MenuConverter;
import me.pi.cloud.admin.mapper.MenuMapper;
import me.pi.cloud.admin.pojo.dto.MenuDTO;
import me.pi.cloud.admin.pojo.po.SysMenu;
import me.pi.cloud.admin.pojo.po.SysRole;
import me.pi.cloud.admin.pojo.query.MenuTreeQuery;
import me.pi.cloud.admin.pojo.vo.CurrentUserMenuTreeVO;
import me.pi.cloud.admin.pojo.vo.MenuTreeVO;
import me.pi.cloud.admin.service.MenuService;
import me.pi.cloud.admin.service.RoleService;
import me.pi.cloud.common.redis.constant.CacheConstants;
import me.pi.cloud.common.web.constant.PiConstants;
import me.pi.cloud.common.web.enums.MenuTypeEnum;
import me.pi.cloud.common.web.exception.BadRequestException;
import me.pi.cloud.common.web.vo.SelectTreeVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, SysMenu> implements MenuService {
    private final MenuMapper menuMapper;
    private final MenuConverter menuConverter;
    public final RoleService roleService;

    @Override
    public List<MenuTreeVO> getMenuTree(MenuTreeQuery query) {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery(SysMenu.class)
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysMenu::getName, query.getKeyWord())
                .or()
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysMenu::getComponent, query.getKeyWord())
                .or()
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysMenu::getPermission, query.getKeyWord())
                .orderByAsc(SysMenu::getSort)
                .select(SysMenu::getId,
                        SysMenu::getCreateTime, SysMenu::getName, SysMenu::getPath, SysMenu::getComponentName,
                        SysMenu::getComponent, SysMenu::getPermission, SysMenu::getIcon, SysMenu::getSort,
                        SysMenu::getKeepAlive, SysMenu::getType, SysMenu::getExternalLinks, SysMenu::getVisible,
                        SysMenu::getRedirect, SysMenu::getParentId);

        List<SysMenu> menuList = super.list(wrapper);

        if (CollUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }

        if (StrUtil.isBlank(query.getKeyWord())) {
            return this.buildMenuTree(PiConstants.TREE_ROOT_ID, menuList);
        }

        List<Long> ids = menuList.stream().map(SysMenu::getId).collect(Collectors.toList());
        return menuList.stream().map(menu -> {
            if (!ids.contains(menu.getParentId())) {
                ids.add(menu.getParentId());
                return buildMenuTree(menu.getParentId(), menuList);
            }
            return new ArrayList<MenuTreeVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    @Override
    @CacheEvict(value = CacheConstants.CACHE_MENU, allEntries = true)
    public void saveOrUpdate(MenuDTO dto) {
        if (dto.getParentId().equals(dto.getId())) {
            throw new BadRequestException("上级类目不能为自己！");
        }
        if (dto.getType().equals(MenuTypeEnum.DIR.getType()) && dto.getExternalLinks() != 1) {
            dto.setComponent("Navigation");
        }
        SysMenu menu = menuConverter.menuDtoToSysMenu(dto);
        super.saveOrUpdate(menu);

        // 更新 hasChildren
        if(dto.getParentId() != 0){
            SysMenu sysMenu = super.getOne(Wrappers.lambdaQuery(SysMenu.class)
                    .eq(SysMenu::getId, dto.getParentId())
                    .select(SysMenu::getHasChildren));
            Optional.of(sysMenu).ifPresent(e -> {
                if(e.getHasChildren() == 0){
                    super.update(Wrappers.lambdaUpdate(SysMenu.class)
                            .set(SysMenu::getHasChildren, 1)
                            .eq(SysMenu::getId, dto.getParentId()));
                }
            });
        }
    }

    @Override
    @Transactional
    public void deleteMenu(String ids) {
        Set<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        Set<Long> toBeDeletedIdSet = new HashSet<>();

        idSet.forEach(id -> {
            if (!toBeDeletedIdSet.contains(id)) {
                toBeDeletedIdSet.add(id);
                getToBeDeletedChildrenIds(id, toBeDeletedIdSet);
            }
        });

        HashSet<Long> parentMenuIdSet = new HashSet<>();
        idSet.forEach(id -> {
            // 父菜单
            SysMenu parentMenu = super.getOne(Wrappers.lambdaQuery(SysMenu.class)
                    .eq(SysMenu::getId, id).select(SysMenu::getParentId));
            if(parentMenu != null){
                parentMenuIdSet.add(parentMenu.getParentId());
            }
        });

        super.removeByIds(toBeDeletedIdSet);

        // 更新 hasChildren
        Set<Long> toBeUpdateHasChildrenId = new HashSet<>();
        parentMenuIdSet.forEach(id -> {
            // 父菜单
            SysMenu parentMenu = super.getOne(Wrappers.lambdaQuery(SysMenu.class)
                    .eq(SysMenu::getId, id).select(SysMenu::getId));
            if(parentMenu != null){
                // 父菜单是否有子菜单
                long count = super.count(Wrappers.lambdaQuery(SysMenu.class)
                        .eq(SysMenu::getParentId, parentMenu.getId()));
                if(count <= 0) {
                    toBeUpdateHasChildrenId.add(parentMenu.getId());
                }
            }
        });
        if(CollUtil.isNotEmpty(toBeUpdateHasChildrenId)){
            super.update(Wrappers.lambdaUpdate(SysMenu.class)
                    .set(SysMenu::getHasChildren, 0)
                    .in(SysMenu::getId, toBeUpdateHasChildrenId));
        }
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_MENU, key = "#p0")
    public List<CurrentUserMenuTreeVO> buildMenu(String username) {
        // 角色标识
        List<SysRole> sysRoleList = roleService.listRoleByUserName(username);
        List<String> roleCodeList = sysRoleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList());

        return buildCurrentUserMenuTree(PiConstants.TREE_ROOT_ID, menuMapper.listMenuByRoleCodeList(roleCodeList));
    }

    @Override
    public List<SelectTreeVO> getMenuSelectTree(Boolean containsButtons) {
        List<SysMenu> menuList = super.list(Wrappers.lambdaQuery(SysMenu.class)
                .ne(!containsButtons, SysMenu::getType, MenuTypeEnum.BUTTON.getType())
                .select(SysMenu::getId, SysMenu::getName, SysMenu::getParentId)
                .orderByAsc(SysMenu::getSort)
        );
        if (CollUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        return buildMenuSelectTree(PiConstants.TREE_ROOT_ID, menuList);
    }

    @Override
    public List<SysMenu> listPermissionByRoleIds(Long[] ids) {
        return menuMapper.listPermissionByRoleIds(ids);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return menuMapper.getMenuIdsByRoleId(roleId);
    }

    private List<MenuTreeVO> buildMenuTree(Long parentId, List<SysMenu> menuList) {
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    MenuTreeVO menuTreeVO = menuConverter.sysMenuToMenuTreeVo(menu);
                    menuTreeVO.setChildren(buildMenuTree(menu.getId(), menuList));
                    return menuTreeVO;
                }).collect(Collectors.toList());
    }

    private void getToBeDeletedChildrenIds(Long parentId, Set<Long> toBeDeletedIdSet) {
        List<SysMenu> childrenMenuList = super.list(Wrappers.lambdaQuery(SysMenu.class)
                .eq(SysMenu::getParentId, parentId)
                .select(SysMenu::getId, SysMenu::getParentId));

        childrenMenuList.forEach(childrenMenu -> {
            toBeDeletedIdSet.add(childrenMenu.getId());
            getToBeDeletedChildrenIds(childrenMenu.getId(), toBeDeletedIdSet);
        });
    }

    private List<CurrentUserMenuTreeVO> buildCurrentUserMenuTree(Long parentId, List<SysMenu> menuList) {
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    CurrentUserMenuTreeVO currentUserMenuTreeVO = menuConverter.sysMenuToCurrentUserMenuTreeVo(menu);
                    if (MenuTypeEnum.MENU.getType().equals(menu.getType())) {
                        if (StrUtil.isNotBlank(menu.getComponentName())) {
                            currentUserMenuTreeVO.setName(menu.getComponentName());
                        } else {
                            currentUserMenuTreeVO.setName(menu.getName());
                        }
                    }
                    currentUserMenuTreeVO.setChildren(buildCurrentUserMenuTree(menu.getId(), menuList));
                    return currentUserMenuTreeVO;
                })
                .collect(Collectors.toList());
    }

    private List<SelectTreeVO> buildMenuSelectTree(Long parentId, List<SysMenu> menuList) {
        return menuList.stream().filter(e -> e.getParentId().equals(parentId))
                .map(menu -> {
                    SelectTreeVO selectTree = menuConverter.sysMenuToSelectTreeVo(menu);
                    selectTree.setChildren(buildMenuSelectTree(menu.getId(), menuList));
                    return selectTree;
                }).collect(Collectors.toList());
    }
}
