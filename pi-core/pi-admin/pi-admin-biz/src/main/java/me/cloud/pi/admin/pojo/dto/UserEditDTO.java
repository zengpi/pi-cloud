package me.cloud.pi.admin.pojo.dto;

import lombok.Data;

/**
 * 用户修改基本信息
 * @author ZnPi
 * @date 2022-08-27
 */
@Data
public class UserEditDTO {
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 新密码，如果此字段不为空，oldPassword 字段也不允许为空
     */
    private String password;
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机
     */
    private String phone;
}
