/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.pi.cloud.admin.pojo.po;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pi.cloud.common.mybatis.base.BaseEntity;

/**
 * @author ZnPi
 * @date 2022-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRegisteredClient extends BaseEntity {
    /**
     * 客户端 ID
     */
    private String clientId;
    /**
     * 客户端密码
     */
    private String clientSecret;
    /**
     * 客户端密码过期时间
     */
    private DateTime clientSecretExpiresAt;
    /**
     * 客户端名称
     */
    private String clientName;
    /**
     * 客户端认证方式（client_secret_basic，client_secret_post，private_key_jwt，client_secret_jwt，none）
     */
    private String clientAuthenticationMethods;
    /**
     * 授权类型（authorization_code,client_credentials,refresh_token,password）
     */
    private String authorizationGrantTypes;
    /**
     * 重定向 URI
     */
    private String redirectUris;
    /**
     * SCOPE
     */
    private String scopes;
    /**
     * 是否要求授权许可（1:=是；0:=否）
     */
    private Integer requireAuthorizationConsent;
    /**
     * 访问令牌有效期（单位：s）
     */
    private Integer accessTokenTimeToLive;
    /**
     * 访问令牌格式（self-contained, reference）
     */
    private String accessTokenFormat;
    /**
     * 刷新令牌有效期（单位：s）
     */
    private Integer refreshTokenTimeToLive;
    /**
     * 是否删除（0:=未删除;null:=已删除）
     */
    private Integer deleted;
}
