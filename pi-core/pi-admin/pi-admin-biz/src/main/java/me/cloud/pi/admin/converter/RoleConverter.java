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
