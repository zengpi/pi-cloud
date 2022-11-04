package me.cloud.pi.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cloud.pi.admin.pojo.po.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
public interface MenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据角色 ID 获取权限标识
     * @param ids 角色 ID
     * @return 权限标识
     */
    List<SysMenu> listPermissionByRoleIds(@Param("ids") Long[] ids);

    /**
     * 根据角色编码列表获取菜单
     * @param roleCodeList 角色编码列表
     * @return 菜单
     */
    List<SysMenu> listMenuByRoleCodeList(@Param("roleCodeList") List<String> roleCodeList);
}
