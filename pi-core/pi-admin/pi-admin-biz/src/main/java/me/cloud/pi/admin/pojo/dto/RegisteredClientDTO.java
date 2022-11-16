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

package me.cloud.pi.admin.pojo.dto;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @author ZnPi
 * @date 2022-11-15
 */
@Data
public class RegisteredClientDTO {
    /**
     * 唯一标识
     */
    private Long id;
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
}
