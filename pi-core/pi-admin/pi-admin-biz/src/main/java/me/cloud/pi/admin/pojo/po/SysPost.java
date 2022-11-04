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
public class SysPost extends BaseEntity {
    /**
     * 岗位编码
     */
    private String postCode;
    /**
     * 岗位名称
     */
    private String postName;
    /**
     * 排序
     */
    private Integer seq;
    /**
     * 备注
     */
    private String remark;
}
