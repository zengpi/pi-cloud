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


import me.cloud.pi.admin.pojo.dto.UserImportDTO;
import me.cloud.pi.admin.pojo.form.UserEditForm;
import me.cloud.pi.admin.pojo.form.UserForm;
import me.cloud.pi.admin.pojo.po.SysUser;
import me.cloud.pi.admin.pojo.vo.UserInfoVO;
import me.cloud.pi.admin.pojo.vo.UserVO;
import org.mapstruct.Mapper;

/**
 * @author ZnPi
 * @date 2022-08-30
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
    /**
     * 表单转 po
     * @param form 表单
     * @return po
     */
    SysUser userFormToUserPo(UserForm form);

    /**
     * 表单转 po
     * @param form 表单
     * @return po
     */
    SysUser userEditFormToUserPo(UserEditForm form);

    /**
     * vo 转 po
     * @param vo vo
     * @return po
     */
    SysUser userVoToUserPo(UserVO vo);

    /**
     * fileItem 转 po
     * @return po
     */
    SysUser fileItemToUserPo(UserImportDTO.FileItem fileItem);

    /**
     * po -> user info
     * @param user /
     * @return /
     */
    UserInfoVO.UserInfo userPoToUserInfo(SysUser user);
}
