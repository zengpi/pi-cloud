package me.cloud.pi.admin.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cloud.pi.common.mybatis.base.BaseEntity;

/**
 * @author ZnPi
 * @date 2022-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {
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
    /**
     * 是否删除（0:=未删除; null:=已删除）
     */
    private Integer deleted;
}
