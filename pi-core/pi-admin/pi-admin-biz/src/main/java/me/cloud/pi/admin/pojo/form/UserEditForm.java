package me.cloud.pi.admin.pojo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户编辑表单
 * @author ZnPi
 * @date 2022-09-06
 */
@Data
public class UserEditForm {
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    /**
     * 密码
     */
    private String password;
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
     * 角色 ID 列表
     */
    @NotNull(message = "roleIds 不能为 null")
    private List<Long> roleIds;
    /**
     * 状态（0:=禁用，1:=启用）
     */
    private Integer status;
}
