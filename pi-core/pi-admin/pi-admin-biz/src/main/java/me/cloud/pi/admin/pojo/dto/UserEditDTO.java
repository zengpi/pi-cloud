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

import lombok.Data;

/**
 * 用户修改基本信息
 * @author ZnPi
 * @date 2022-08-27
 */
@Data
public class UserEditDTO {
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 新密码，如果此字段不为空，oldPassword 字段也不允许为空
     */
    private String password;
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机
     */
    private String phone;
}
