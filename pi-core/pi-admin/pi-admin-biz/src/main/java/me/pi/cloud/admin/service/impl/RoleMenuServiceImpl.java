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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.pi.cloud.admin.mapper.RoleMenuMapper;
import me.pi.cloud.admin.pojo.dto.RoleMenuAllocationDTO;
import me.pi.cloud.admin.pojo.po.SysRoleMenu;
import me.pi.cloud.admin.service.RoleMenuService;
import me.pi.cloud.common.redis.constant.CacheConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements RoleMenuService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {CacheConstants.CACHE_MENU, CacheConstants.CACHE_USER}, allEntries = true)
    public void roleMenuAllocation(RoleMenuAllocationDTO dto) {
        Set<Long> menuIdSet = null;
        if (StrUtil.isNotBlank(dto.getMenuIds())) {
            menuIdSet = Arrays.stream(dto.getMenuIds().split(",")).map(Long::valueOf).collect(Collectors.toSet());
        }

        Set<Long> curMenuIdSet = super.list(Wrappers.lambdaQuery(SysRoleMenu.class)
                        .eq(SysRoleMenu::getRoleId, dto.getRoleId())
                        .select(SysRoleMenu::getMenuId))
                .stream().map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());


        // 待删除
        Set<Long> toBeDeletedRoleMenuIds;
        // 待新增
        Set<SysRoleMenu> toBeAddedRoleMenus = null;

        if (menuIdSet == null) {
            toBeDeletedRoleMenuIds = curMenuIdSet;
        } else {
            toBeAddedRoleMenus = menuIdSet.stream()
                    .filter(e -> !curMenuIdSet.contains(e))
                    .map(e -> new SysRoleMenu(dto.getRoleId(), e))
                    .collect(Collectors.toSet());

            Set<Long> finalMenuIdSet = menuIdSet;
            toBeDeletedRoleMenuIds = curMenuIdSet.stream().filter(e -> !finalMenuIdSet.contains(e)).collect(Collectors.toSet());
        }

        if (toBeDeletedRoleMenuIds.size() > 0) {
            super.remove(Wrappers.lambdaQuery(SysRoleMenu.class)
                    .eq(SysRoleMenu::getRoleId, dto.getRoleId())
                    .in(SysRoleMenu::getMenuId, toBeDeletedRoleMenuIds));
        }
        if (toBeAddedRoleMenus != null && toBeAddedRoleMenus.size() > 0) {
            super.saveBatch(toBeAddedRoleMenus);
        }
    }
}
