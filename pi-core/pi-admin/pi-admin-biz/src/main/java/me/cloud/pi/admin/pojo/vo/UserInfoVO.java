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

package me.cloud.pi.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
@Data
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 5131551841734401217L;
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
    public static class UserInfo implements Serializable{
        private static final long serialVersionUID = 2028677663877849897L;
        private Long id;
        private String username;
        private String nickname;
        private String phone;
        private String avatar;
    }
}
