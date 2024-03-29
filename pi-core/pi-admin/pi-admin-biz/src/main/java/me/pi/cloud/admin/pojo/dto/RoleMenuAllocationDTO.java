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
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Schema(name = "角色菜单分配 DTO")
@Data
public class RoleMenuAllocationDTO {
    @Schema(description = "角色 ID")
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;

    @Schema(description = "菜单 ID 列表，多个以逗号分隔")
    private String menuIds;
}
