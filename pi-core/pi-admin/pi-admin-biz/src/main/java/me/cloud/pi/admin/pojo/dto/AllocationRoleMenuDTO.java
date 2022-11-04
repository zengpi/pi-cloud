package me.cloud.pi.admin.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Data
public class AllocationRoleMenuDTO {
    /**
     * 角色 ID
     */
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;
    /**
     * 菜单 ID 列表，多个以逗号分隔
     */
    private String menuIds;
}
