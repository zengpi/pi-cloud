package me.cloud.pi.admin.pojo.vo;

import lombok.Data;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Data
public class OptionalUserVO {
    /**
     * 主键
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
