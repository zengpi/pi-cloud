package me.cloud.pi.admin.pojo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Data
public class UserForm implements Serializable {
    private static final long serialVersionUID = -8473734175233904491L;

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
     * 性别
     */
    private Integer sex;
    /**
     * 手机
     */
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "手机号格式错误")
    private String phone;
    /**
     * 部门 ID
     */
    private Long deptId;
    /**
     * 角色 ID
     */
    private List<Long> roleIds;
}
