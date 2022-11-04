package me.cloud.pi.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.cloud.pi.admin.pojo.dto.AllocationRoleMenuDTO;
import me.cloud.pi.admin.pojo.po.SysRoleMenu;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
public interface RoleMenuService extends IService<SysRoleMenu> {
    /**
     * 根据角色 ID 获取菜单 ID 列表
     * @param roleId 角色 ID
     * @return 菜单 ID 列表
     */
    List<Long> getMenuLeafIdsByRoleId(Long roleId);

    /**
     * 为角色分配菜单
     * @param dto /
     */
    void allocationRoleMenu(AllocationRoleMenuDTO dto);
}
