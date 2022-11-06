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

package me.cloud.pi.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cloud.pi.admin.pojo.po.SysRole;
import me.cloud.pi.admin.pojo.po.SysUserRole;
import me.cloud.pi.admin.pojo.query.RoleMemberQueryParam;
import me.cloud.pi.admin.pojo.vo.RoleMemberVO;
import me.cloud.pi.common.mybatis.util.PiPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
public interface RoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户 ID 获取该用户的角色
     *
     * @param id 用户 ID
     * @return 该用户的角色
     */
    List<SysRole> listRoleByUserId(@Param("id") Long id);

    /**
     * 获取角色成员
     *
     * @param page       分页
     * @param queryParam 查询参数
     * @return 角色成员列表
     */
    PiPage<RoleMemberVO> getRoleMembers(@Param("page") PiPage<RoleMemberVO> page, @Param("queryParam") RoleMemberQueryParam queryParam);

    /**
     * 给定的 userRole 是否存在
     *
     * @param userRole /
     * @return 1:存在，null：不存在
     */
    Integer isExistRoleUser(@Param("userRole") SysUserRole userRole);
}
