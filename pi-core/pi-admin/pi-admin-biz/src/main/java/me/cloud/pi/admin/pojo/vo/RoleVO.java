package me.cloud.pi.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@Data
public class RoleVO implements Serializable {
    private static final long serialVersionUID = 374363160059522111L;

    /**
     * 角色 ID
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String roleDesc;
}
