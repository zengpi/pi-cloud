package me.cloud.pi.admin.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-09-25
 */
@Data
public class RoleDTO {
    /**
     * 唯一标识
     */
    private String id;
    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空")
    private String name;

    /**
     * 角色编码
     */
    @NotNull(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色描述
     */
    private String roleDesc;
}
