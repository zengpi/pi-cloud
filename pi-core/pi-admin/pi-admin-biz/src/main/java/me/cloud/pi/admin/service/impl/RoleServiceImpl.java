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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.converter.RoleConverter;
import me.cloud.pi.admin.mapper.RoleMapper;
import me.cloud.pi.admin.pojo.dto.RoleUserAllocationDTO;
import me.cloud.pi.admin.pojo.dto.RoleDTO;
import me.cloud.pi.admin.pojo.po.SysRole;
import me.cloud.pi.admin.pojo.po.SysUserRole;
import me.cloud.pi.admin.pojo.vo.RoleVO;
import me.cloud.pi.admin.service.RoleService;
import me.cloud.pi.admin.service.UserRoleService;
import me.cloud.pi.common.mybatis.base.BaseQuery;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.redis.constant.CacheConstants;
import me.cloud.pi.common.web.exception.BadRequestException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-08-20
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleConverter roleConverter;

    @Override
    public PiPage<RoleVO> getRoles(BaseQuery query) {
        PiPage<SysRole> page = new PiPage<>(query.getPageNum(), query.getPageSize());

        PiPage<SysRole> roles = super.page(page, Wrappers.lambdaQuery(SysRole.class)
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysRole::getName, query.getKeyWord())
                .or()
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysRole::getRoleCode, query.getKeyWord())
                .select(SysRole::getId, SysRole::getName, SysRole::getRoleCode, SysRole::getRoleDesc)
        );
        return roleConverter.sysRolePageToRoleVoPage(roles);
    }

    @Override
    public void saveOrUpdate(RoleDTO dto) {
        super.saveOrUpdate(roleConverter.roleDtoToSysRole(dto));
    }

    @Override
    public void deleteRole(String ids) {
        if (StrUtil.isBlank(ids)) {
            throw new BadRequestException("ids 不能为空");
        }
        Set<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        super.removeByIds(idSet);
    }

    @Override
    public List<SysRole> listRoleByUserId(Long id) {
        return roleMapper.listRoleByUserId(id);
    }

    @Override
    public List<SysRole> listRoleByUserName(String username) {
        return roleMapper.listRoleByUsername(username);
    }

    @Override
    public List<RoleVO> getAllRoles() {
        List<SysRole> sysRoleList = super.list(Wrappers.lambdaQuery(SysRole.class)
                .select(SysRole::getId, SysRole::getName, SysRole::getRoleCode, SysRole::getRoleDesc));
        return roleConverter.sysRoleListToRoleVoList(sysRoleList);
    }
}
