package me.cloud.pi.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Data
public class UserVO implements Serializable {
    public static final long serialVersionUID = -1L;
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
    private Integer deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 角色 ID 列表，多个以逗号分隔
     */
    private List<Long> roleIds;
    /**
     * 状态（0:=禁用，1:=启用）
     */
    private Integer enabled;
    /**
     * 创建时间
     */
    private Date createTime;
}
