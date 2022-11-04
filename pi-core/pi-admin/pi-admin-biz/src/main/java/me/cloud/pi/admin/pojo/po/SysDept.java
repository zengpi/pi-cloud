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
public class SysDept extends BaseEntity {
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父部门 ID
     */
    private Long parentId;

    /**
     * 是否删除（0:=未删除, 1:=已删除）
     */
    private Integer deleted;
}
