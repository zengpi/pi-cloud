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

package me.pi.cloud.admin.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
@Schema(title = "用户信息")
@Data
public class UserInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5131551841734401217L;

    @Schema(description = "用户信息")
    private UserInfo userInfo;

    @Schema(description = "权限标识")
    private String[] authorities;

    @Schema(description = "角色")
    private String[] roles;

    @Schema(description = "用户信息")
    @Data
    public static class UserInfo implements Serializable{
        private static final long serialVersionUID = 2028677663877849897L;

        @Schema(description = "标识")
        private Long id;

        @Schema(description = "用户名")
        private String username;

        @Schema(description = "昵称")
        private String nickname;

        @Schema(description = "手机")
        private String phone;

        @Schema(description = "头像")
        private String avatar;
    }
}
