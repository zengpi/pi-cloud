package me.cloud.pi.admin.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cloud.pi.common.mybatis.base.BaseEntity;

/**
 * @author ZnPi
 * @date 2022-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 手机
     */
    private String phone;
    /**
     * 部门 ID
     */
    private Long deptId;
    /**
     * 状态（0:=禁用，1:=启用）
     */
    private Integer enabled;
    /**
     * 是否删除（0:=未删除，1:=已删除）
     */
    private Integer deleted;
}
