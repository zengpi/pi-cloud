package me.cloud.pi.auth.constant;

/**
 * @author ZnPi
 * @date 2022-10-22
 */
public interface SecurityConstants {
    /**
     * 授权标识
     */
    String AUTHORIZATION = "Authorization";

    /**
     * token 前缀
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";
}
