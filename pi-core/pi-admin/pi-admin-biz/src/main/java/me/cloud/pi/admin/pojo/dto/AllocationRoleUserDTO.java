package me.cloud.pi.admin.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Data
public class AllocationRoleUserDTO {
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;
    /**
     * 分配用户 ID，多个以逗号分隔
     */
    private String addUserIds;
    /**
     * 移除用户 ID，多个以逗号分隔
     */
    private String removeUserIds;
}
