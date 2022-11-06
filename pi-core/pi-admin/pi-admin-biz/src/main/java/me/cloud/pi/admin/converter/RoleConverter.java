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

package me.cloud.pi.admin.converter;


import me.cloud.pi.admin.pojo.dto.RoleDTO;
import me.cloud.pi.admin.pojo.po.SysRole;
import me.cloud.pi.admin.pojo.vo.RoleVO;
import me.cloud.pi.common.mybatis.util.PiPage;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {
    /**
     * List<PO> -> List<VO>
     * @param roleList /
     * @return /
     */
    List<RoleVO> rolePoListToRoleVoList(List<SysRole> roleList);

    /**
     * po page -> vo page
     * @param roles /
     * @return /
     */
    PiPage<RoleVO> rolePoPageToRoleVoPage(PiPage<SysRole> roles);

    /**
     * dto -> po
     * @param dto /
     * @return /
     */
    SysRole roleDtoToRolePo(RoleDTO dto);
}
