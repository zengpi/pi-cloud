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

package me.pi.cloud.admin.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pi.cloud.common.mybatis.base.BaseEntity;

/**
 * @author ZnPi
 * @date 2022-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {
    /**
     * 菜单名称
     */
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
     * 父目录 ID（0表示根目录）
     */
    private Long parentId;
    /**
     * 是否拥有子节点（0：否；1：是）
     */
    private Integer hasChildren;
    /**
     * 是否删除（0:=否，null:=是）
     */
    private Integer deleted;
}
