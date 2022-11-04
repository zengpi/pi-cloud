package me.cloud.pi.admin.pojo.vo;

import lombok.Data;

/**
 * @author ZnPi
 * @date 2022-09-25
 */
@Data
public class RoleMemberVO {
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 部门名称
     */
    private String deptName;
}
