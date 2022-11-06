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

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-09-24
 */
@Data
public class MenuDTO {
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;
    /**
     * 路由路径（浏览器地址栏路径）
     */
    private String path;
    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 组件路径（vue页面完整路径，省略.vue后缀）
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否缓存（0:=关闭, 1:=开启）
     */
    private Integer keepAlive;
    /**
     * 菜单类型（1:=菜单, 2:=目录，3:=按钮）
     */
    private Integer type;
    /**
     * 是否外链（0:=否, 1:=是）
     */
    private Integer externalLinks;
    /**
     * 是否可见（0:=不可见，1：可见）
     */
    private Integer visible;
    /**
     * 重定向路径
     */
    private String redirect;
    /**
     * 父目录 ID（-1表示根目录）
     */
    @NotNull(message = "上级类目不能为空")
    private Long parentId;
}
