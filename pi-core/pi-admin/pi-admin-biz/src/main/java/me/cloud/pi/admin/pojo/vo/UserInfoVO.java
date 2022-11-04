package me.cloud.pi.admin.pojo.vo;

import lombok.Data;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
@Data
public class UserInfoVO {
    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 权限标识
     */
    private String[] authorities;

    /**
     * 角色
     */
    private String[] roles;

    @Data
    public static class UserInfo {
        private Long id;
        private String username;
        private String nickname;
        private String phone;
        private String avatar;
    }
}
