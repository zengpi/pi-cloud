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

package me.pi.cloud.admin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Schema(name = "用户 DTO")
@Data
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8473734175233904491L;

    @Schema(description = "标识")
    private Long id;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "性别(1:=男; 2:=女)")
    private Integer sex;

    @Schema(description = "生日")
    private Date bday;

    @Schema(description = "手机")
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "手机号格式错误")
    private String phone;

    @Schema(description = "部门 ID")
    private Long deptId;

    @Schema(description = "角色 ID")
    @NotEmpty(message = "至少指定一个角色")
    private List<Long> roleIds;

    @Schema(description = "状态（0:=禁用，1:=启用）")
    private Integer enabled;
}
