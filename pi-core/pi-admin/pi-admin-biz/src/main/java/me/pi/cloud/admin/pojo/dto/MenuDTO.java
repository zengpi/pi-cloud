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
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ZnPi
 * @date 2022-09-24
 */
@Data
@Schema(title = "菜单 DTO")
public class MenuDTO {
    @Schema(description = "标识")
    private Long id;

    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @Schema(description = "路由路径（浏览器地址栏路径）")
    private String path;

    @Schema(description = "组件名称")
    private String componentName;

    @Schema(description = "组件路径（vue页面完整路径，省略.vue后缀）")
    private String component;

    @Schema(description = "权限标识")
    private String permission;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否缓存（0:=关闭, 1:=开启）")
    private Integer keepAlive;

    @Schema(description = "菜单类型（1:=菜单, 2:=目录，3:=按钮）")
    private Integer type;

    @Schema(description = "是否外链（0:=否, 1:=是）")
    private Integer externalLinks;

    @Schema(description = "是否可见（0:=不可见，1：可见）")
    private Integer visible;

    @Schema(description = "重定向路径")
    private String redirect;

    @Schema(description = "父目录 ID（0表示根目录）")
    @NotNull(message = "上级类目不能为空")
    private Long parentId;
}
