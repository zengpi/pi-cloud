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

package me.cloud.pi.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.converter.MenuConverter;
import me.cloud.pi.admin.mapper.MenuMapper;
import me.cloud.pi.admin.pojo.dto.MenuDTO;
import me.cloud.pi.admin.pojo.po.SysMenu;
import me.cloud.pi.admin.pojo.query.MenuQueryParam;
import me.cloud.pi.admin.pojo.vo.MenuVO;
import me.cloud.pi.admin.service.MenuService;
import me.cloud.pi.common.security.util.SecurityUtils;
import me.cloud.pi.common.web.constant.PiConstants;
import me.cloud.pi.common.web.enums.MenuTypeEnum;
import me.cloud.pi.common.web.exception.BadRequestException;
import me.cloud.pi.common.web.pojo.vo.SelectTreeVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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

    @Override
    public List<SysMenu> listPermissionByRoleIds(Long[] ids) {
        return menuMapper.listPermissionByRoleIds(ids);
    }

    @Override
    public List<Tree<Long>> buildMenu() {
        ArrayList<String> roleCodeList = (ArrayList<String>) SecurityUtils.getRoleCodeList();
        if (roleCodeList.size() == 0) {
            return Collections.emptyList();
        }
        // 根据角色编码列表获取菜单
        Set<SysMenu> menuSet = new HashSet<>(menuMapper.listMenuByRoleCodeList(roleCodeList));
        List<TreeNode<Long>> treeNodes = menuSet.stream()
                .filter(menu -> !MenuTypeEnum.BUTTON.getType().equals(menu.getType()))
                .filter(menu -> StrUtil.isNotBlank(menu.getPath()))
                .map(genTreeNode()).collect(Collectors.toList());
        return TreeUtil.build(treeNodes, PiConstants.MENU_TREE_ROOT_ID);
    }

    @Override
    public List<MenuVO> getMenus(MenuQueryParam query) {
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

        List<SysMenu> menus = super.list(wrapper);

        if (CollUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }

        // 查询所有
        if (StrUtil.isBlank(query.getKeyWord())) {
            return this.buildMenuTree(menus, PiConstants.MENU_TREE_ROOT_ID);
        }

        // 条件查询
        List<Long> ids = menus.stream().map(SysMenu::getId).collect(Collectors.toList());
        return menus.stream().map(e -> {
            if (!ids.contains(e.getParentId())) {
                ids.add(e.getParentId());
                return buildMenuTree(menus, e.getParentId());
            }
            return new ArrayList<MenuVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    @Override
    public List<SelectTreeVO> selectTree(boolean containBtn) {
        List<SysMenu> menus = super.list(Wrappers.lambdaQuery(SysMenu.class)
                .ne(!containBtn, SysMenu::getType, MenuTypeEnum.BUTTON.getType())
                .select(SysMenu::getId, SysMenu::getName, SysMenu::getParentId)
        );
        if (CollUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }
        return buildSelectTree(menus, PiConstants.MENU_TREE_ROOT_ID);
    }

    @Override
    public void saveOrUpdate(MenuDTO dto) {
        if (dto.getParentId().equals(dto.getId())) {
            throw new BadRequestException("上级类目不能为自己！");
        }
        if (dto.getType() == 1) {
            dto.setComponentName("Layout");
        }
        SysMenu menu = menuConverter.menuDtoToMenuPo(dto);
        super.saveOrUpdate(menu);
    }

    @Override
    public void delete(String ids) {
        Set<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        List<SysMenu> menus = super.list(Wrappers.lambdaQuery(SysMenu.class)
                .in(SysMenu::getId, idSet)
                .select(SysMenu::getId, SysMenu::getParentId));
        Set<SysMenu> topMenus = new HashSet<>(idSet.size());
        menus.forEach(e -> {
            if (!idSet.contains(e.getParentId())) {
                topMenus.add(e);
            }
        });
        getChildrenDeleteMenus(topMenus, idSet);
        super.removeByIds(idSet);
    }

    private void getChildrenDeleteMenus(Set<SysMenu> menus, Set<Long> idSet) {
        menus.forEach(e -> {
            List<SysMenu> childMenus = super.list(Wrappers.lambdaQuery(SysMenu.class)
                    .eq(SysMenu::getParentId, e.getId())
                    .select(SysMenu::getId, SysMenu::getParentId));
            if (childMenus.size() > 0) {
                idSet.addAll(childMenus.stream().map(SysMenu::getId).collect(Collectors.toSet()));
                getChildrenDeleteMenus(new HashSet<>(childMenus), idSet);
            }
        });
    }

    private List<SelectTreeVO> buildSelectTree(List<SysMenu> menus, Long parentId) {
        return menus.stream().filter(e -> e.getParentId().equals(parentId))
                .map(e -> {
                    SelectTreeVO selectTree = menuConverter.menuPoToSelectTreeVo(e);
                    selectTree.setChildren(buildSelectTree(menus, e.getId()));
                    return selectTree;
                }).collect(Collectors.toList());
    }

    private List<MenuVO> buildMenuTree(List<SysMenu> menus, Long parentId) {
        return menus.stream()
                .filter(e -> e.getParentId().equals(parentId))
                .map(e -> {
                    MenuVO menu = menuConverter.menuPoToMenuVo(e);
                    menu.setChildren(buildMenuTree(menus, e.getId()));
                    return menu;
                }).collect(Collectors.toList());
    }

    /**
     * 生成树节点
     *
     * @return 树节点
     */
    private Function<SysMenu, TreeNode<Long>> genTreeNode() {
        return menu -> {
            TreeNode<Long> treeNode = new TreeNode<>();

            treeNode.setId(menu.getId());
            treeNode.setWeight(menu.getSort());
            treeNode.setParentId(menu.getParentId());

            HashMap<String, Object> extra = new HashMap<>(7);
            extra.put("component", menu.getComponent());
            extra.put("path", menu.getPath());
            extra.put("redirect", menu.getRedirect());
            if (MenuTypeEnum.MENU.getType().equals(menu.getType())) {
                extra.put("name", menu.getComponentName());
            }

            HashMap<String, Object> meta = new HashMap<>(8);

            meta.put("icon", menu.getIcon());
            meta.put("type", menu.getType());
            meta.put("permission", menu.getPermission());
            meta.put("keepAlive", menu.getKeepAlive());
            meta.put("title", menu.getName());
            meta.put("hidden", !menu.getVisible().equals(1));

            extra.put("meta", meta);
            treeNode.setExtra(extra);

            return treeNode;
        };
    }
}
