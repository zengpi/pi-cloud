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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-09-25
 */
@Schema(title = "角色 DTO")
@Data
public class RoleDTO {
    @Schema(description = "标识")
    private String id;

    @Schema(description = "角色名称")
    @NotNull(message = "角色名称不能为空")
    private String name;

    @Schema(description = "角色编码")
    @NotNull(message = "角色编码不能为空")
    private String roleCode;

    @Schema(description = "角色描述")
    private String roleDesc;
}
