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
