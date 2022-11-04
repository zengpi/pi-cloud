package me.cloud.pi.admin.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cloud.pi.common.mybatis.base.BaseEntity;

/**
 * @author ZnPi
 * @date 2022-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysClient extends BaseEntity {
    /**
     * 客户端 ID
     */
    private String clientId;
    /**
     * 客户端秘钥
     */
    private String clientSecret;
    /**
     * 授权类型
     */
    private String grantTypes;
    /**
     * 重定向地址
     */
    private String redirectUri;
    /**
     * 范围
     */
    private String scope;
    /**
     * 刷新令牌有效期（单位：秒）
     */
    private Integer accessTokenValidity;
    /**
     * 访问令牌有效期（单位：秒）
     */
    private Integer refreshTokenValidity;
}
