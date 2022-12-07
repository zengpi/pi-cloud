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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-12-01 20:57
 */
@Schema(title = "当前用户的菜单（树形）")
@Data
public class CurrentUserMenuTreeVO implements Serializable {
    private static final long serialVersionUID = -1686861689952847711L;

    @Schema(description = "标识")
    private Long id;

    @Schema(description = "组件名称")
    private String name;

    @Schema(description = "路由路径（浏览器地址栏路径）")
    private String path;

    @Schema(description = "重定向路径")
    private String redirect;

    @Schema(description = "组件路径（vue页面完整路径，省略.vue后缀）")
    private String component;

    @Schema(description = "父目录 ID（0:=根目录）")
    private Long parentId;

    @Schema(description = "子节点")
    private List<CurrentUserMenuTreeVO> children;

    @Schema(description = "菜单元数据")
    private MetaData meta;

    @Schema(title = "菜单元数据")
    @Data
    public static class MetaData implements Serializable{
        private static final long serialVersionUID = 8405365984926002228L;

        @Schema(description = "菜单标题")
        private String title;

        @Schema(description = "菜单类型（1:=菜单, 2:=目录，3:=按钮）")
        private Integer type;

        @Schema(description = "图标")
        private String icon;

        @Schema(description = "是否隐藏（true:=隐藏，false：不隐藏）")
        private Boolean hidden;

        @Schema(description = "是否缓存（0:=关闭, 1:=开启）")
        private Integer keepAlive;
    }
}
