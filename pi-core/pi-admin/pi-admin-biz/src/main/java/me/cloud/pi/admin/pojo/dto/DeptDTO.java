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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-12-01 13:55
 */
@Schema(title="部门 DTO")
@Data
public class DeptDTO {
    @Schema(description = "标识")
    private Long id;

    @Schema(description = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "父部门 ID")
    @NotNull(message = "上级类目不能为空")
    private Long parentId;
}
