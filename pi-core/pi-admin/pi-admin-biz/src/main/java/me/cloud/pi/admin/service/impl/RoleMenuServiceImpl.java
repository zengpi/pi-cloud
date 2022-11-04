package me.cloud.pi.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.mapper.RoleMenuMapper;
import me.cloud.pi.admin.pojo.dto.AllocationRoleMenuDTO;
import me.cloud.pi.admin.pojo.po.SysMenu;
import me.cloud.pi.admin.pojo.po.SysRoleMenu;
import me.cloud.pi.admin.service.MenuService;
import me.cloud.pi.admin.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements RoleMenuService {
    private final MenuService menuService;

    @Override
    public List<Long> getMenuLeafIdsByRoleId(Long roleId) {
        List<Long> menuIds = super.list(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, roleId)
                .select(SysRoleMenu::getMenuId))
                .stream().map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        if (menuIds.size() == 0) {
            return Collections.emptyList();
        }
        Set<Long> parentIds = menuService.list(Wrappers.lambdaQuery(SysMenu.class)
                .in(SysMenu::getId, menuIds).select(SysMenu::getParentId))
                .stream().map(SysMenu::getParentId).collect(Collectors.toSet());
        return menuIds.stream().filter(e -> !parentIds.contains(e)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void allocationRoleMenu(AllocationRoleMenuDTO dto) {
        Set<Long> menuIdSet = null;
        if (StrUtil.isNotBlank(dto.getMenuIds())) {
            menuIdSet = Arrays.stream(dto.getMenuIds()
                    .split(","))
                    .map(Long::valueOf).collect(Collectors.toSet());
        }

        Set<Long> curMenuIdSet = super.list(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, dto.getRoleId())
                .select(SysRoleMenu::getMenuId))
                .stream().map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());

        // 待新增
        Set<Long> deleteRoleMenuIds;
        // 待删除
        Set<SysRoleMenu> addRoleMenus = null;

        if (menuIdSet == null) {
            deleteRoleMenuIds = curMenuIdSet;
        } else {
            addRoleMenus = menuIdSet.stream()
                    .filter(e -> !curMenuIdSet.contains(e))
                    .map(e -> new SysRoleMenu(dto.getRoleId(), e))
                    .collect(Collectors.toSet());

            Set<Long> finalMenuIdSet = menuIdSet;
            deleteRoleMenuIds = curMenuIdSet.stream().filter(e -> !finalMenuIdSet.contains(e)).collect(Collectors.toSet());
        }

        if (deleteRoleMenuIds.size() > 0) {
            super.remove(Wrappers.lambdaQuery(SysRoleMenu.class)
                    .eq(SysRoleMenu::getRoleId, dto.getRoleId())
                    .in(SysRoleMenu::getMenuId, deleteRoleMenuIds));
        }
        if (addRoleMenus != null && addRoleMenus.size() > 0) {
            super.saveBatch(addRoleMenus);
        }
    }
}
