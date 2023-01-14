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

package me.pi.cloud.auth.constant;

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
