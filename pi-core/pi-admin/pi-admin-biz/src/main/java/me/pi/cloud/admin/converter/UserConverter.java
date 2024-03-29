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

package me.pi.cloud.admin.converter;


import me.pi.cloud.admin.pojo.dto.UserDTO;
import me.pi.cloud.admin.pojo.dto.UserImportDTO;
import me.pi.cloud.admin.pojo.po.SysUser;
import me.pi.cloud.admin.pojo.vo.UserInfoVO;
import org.mapstruct.Mapper;

/**
 * @author ZnPi
 * @date 2022-08-30
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
    /**
     * UserDTO -> SysUser
     * @param dto UserDTO
     * @return SysUser
     */
    SysUser userDtoToSysUser(UserDTO dto);

    /**
     * UserImportDTO.FileItem -> SysUser
     * @param fileItem UserImportDTO.FileItem
     * @return SysUser
     */
    SysUser fileItemToSysUser(UserImportDTO.FileItem fileItem);

    /**
     * SysUser -> UserInfoVO.UserInfo
     * @param user SysUser
     * @return UserInfoVO.UserInfo
     */
    UserInfoVO.UserInfo sysUserToUserInfo(SysUser user);
}
