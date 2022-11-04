package me.cloud.pi.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import me.cloud.pi.admin.pojo.dto.AllocationRoleUserDTO;
import me.cloud.pi.admin.pojo.dto.RoleDTO;
import me.cloud.pi.admin.pojo.po.SysRole;
import me.cloud.pi.admin.pojo.query.RoleMemberQueryParam;
import me.cloud.pi.admin.pojo.query.RoleQueryParam;
import me.cloud.pi.admin.pojo.vo.RoleMemberVO;
import me.cloud.pi.admin.pojo.vo.RoleVO;
import me.cloud.pi.common.mybatis.util.PiPage;

import java.util.List;


/**
 * @author ZnPi
 * @date 2022-08-20
 */
public interface RoleService extends IService<SysRole> {
    /**
     * 根据用户 ID 获取该用户的角色
     * @param id 用户 ID
     * @return 该用户的角色
     */
    List<SysRole> listRoleByUserId(Long id);

    /**
     * 获取所有角色
     * @return 角色列表
     */
    List<RoleVO> getAllRoles();

    /**
     * 角色查询
     * @param queryParam 查询参数
     * @return 角色列表
     */
    PiPage<RoleVO> roles(RoleQueryParam queryParam);

    /**
     * 角色新增
     * @param dto /
     */
    void saveOrUpdate(RoleDTO dto);

    /**
     * 角色删除
     * @param ids 待删除 ID，多个以逗号分隔
     */
    void del(String ids);

    /**
     * 角色成员
     * @param queryParam 查询参数
     * @return 角色成员列表
     */
    PiPage<RoleMemberVO> getRoleMembers(RoleMemberQueryParam queryParam);

    /**
     * 为角色分配用户
     * @param dto /
     */
    void allocationRoleUser(AllocationRoleUserDTO dto);
}
