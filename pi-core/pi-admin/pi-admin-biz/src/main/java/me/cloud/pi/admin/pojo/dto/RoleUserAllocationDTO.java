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
 * @date 2022-09-26
 */
@Schema(name = "角色用户分配 DTO")
@Data
public class RoleUserAllocationDTO {
    @Schema(description = "角色 ID")
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;

    @Schema(description = "待分配用户 ID，多个以逗号分隔")
    private String toBeAddUserIds;

    @Schema(description = "待移除用户 ID，多个以逗号分隔")
    private String toBeRemoveUserIds;
}
