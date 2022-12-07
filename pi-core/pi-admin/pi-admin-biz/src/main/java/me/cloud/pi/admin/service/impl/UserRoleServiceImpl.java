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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.mapper.UserRoleMapper;
import me.cloud.pi.admin.pojo.dto.RoleUserAllocationDTO;
import me.cloud.pi.admin.pojo.po.SysUserRole;
import me.cloud.pi.admin.service.UserRoleService;
import me.cloud.pi.common.redis.constant.CacheConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-08-30
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, SysUserRole> implements UserRoleService {
    private final UserRoleMapper userRoleMapper;

    @Override
    @CacheEvict(value = {CacheConstants.CACHE_USER, CacheConstants.CACHE_MENU}, allEntries = true)
    public void allocationRoleUser(RoleUserAllocationDTO dto) {
        if (StrUtil.isNotBlank(dto.getToBeAddUserIds())) {
            HashSet<SysUserRole> toBeAddUserRoles = new HashSet<>();

            Arrays.stream(dto.getToBeAddUserIds().split(","))
                    .map(Long::valueOf).forEach(e -> {
                        SysUserRole userRole = new SysUserRole(e, dto.getRoleId());
                        Integer exists = userRoleMapper.existsByUserRole(userRole);
                        if (exists != null) {
                            return;
                        }
                        toBeAddUserRoles.add(userRole);
                    });
            if (toBeAddUserRoles.size() > 0) {
                super.saveBatch(toBeAddUserRoles);
            }
        }
        if (StrUtil.isNotBlank(dto.getToBeRemoveUserIds())) {
            Set<Long> removeUserRoleIds = Arrays.stream(dto.getToBeRemoveUserIds().split(","))
                    .map(Long::valueOf).collect(Collectors.toSet());
            if (removeUserRoleIds.size() > 0) {
                super.removeByIds(removeUserRoleIds);
            }
        }
    }
}
