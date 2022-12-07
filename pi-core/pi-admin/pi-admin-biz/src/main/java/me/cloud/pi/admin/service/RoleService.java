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
import me.cloud.pi.admin.pojo.dto.RoleUserAllocationDTO;
import me.cloud.pi.admin.pojo.dto.RoleDTO;
import me.cloud.pi.admin.pojo.po.SysRole;
import me.cloud.pi.admin.pojo.query.RoleMemberQuery;
import me.cloud.pi.admin.pojo.vo.RoleMemberVO;
import me.cloud.pi.admin.pojo.vo.RoleVO;
import me.cloud.pi.common.mybatis.base.BaseQuery;
import me.cloud.pi.common.mybatis.util.PiPage;

import java.util.List;


/**
 * @author ZnPi
 * @date 2022-08-20
 */
public interface RoleService extends IService<SysRole> {
    /**
     * 获取角色
     *
     * @param query 查询参数
     * @return 角色
     */
    PiPage<RoleVO> getRoles(BaseQuery query);

    /**
     * 新增或编辑角色
     *
     * @param dto RoleDTO
     */
    void saveOrUpdate(RoleDTO dto);

    /**
     * 角色删除
     *
     * @param ids 待删除 ID，多个以逗号分隔
     */
    void deleteRole(String ids);

    /**
     * 获取所有角色
     *
     * @return 角色
     */
    List<RoleVO> getAllRoles();

    /**
     * 根据用户 ID 获取该用户的角色
     *
     * @param id 用户 ID
     * @return 该用户的角色
     */
    List<SysRole> listRoleByUserId(Long id);

    /**
     * 根据用户名获取该用户的角色
     *
     * @param username 用户名
     * @return 该用户的角色
     */
    List<SysRole> listRoleByUserName(String username);
}
