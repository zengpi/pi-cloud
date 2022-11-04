package me.cloud.pi.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Data
@NoArgsConstructor
public class SysRoleMenu {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色 ID
     */
    private Long roleId;
    /**
     * 菜单 ID
     */
    private Long menuId;

    public SysRoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
