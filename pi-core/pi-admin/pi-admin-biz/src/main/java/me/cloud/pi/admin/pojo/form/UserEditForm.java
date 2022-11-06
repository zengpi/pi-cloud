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

package me.cloud.pi.admin.pojo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户编辑表单
 * @author ZnPi
 * @date 2022-09-06
 */
@Data
public class UserEditForm {
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 手机
     */
    private String phone;
    /**
     * 部门 ID
     */
    private Long deptId;
    /**
     * 角色 ID 列表
     */
    @NotNull(message = "roleIds 不能为 null")
    private List<Long> roleIds;
    /**
     * 状态（0:=禁用，1:=启用）
     */
    private Integer status;
}
