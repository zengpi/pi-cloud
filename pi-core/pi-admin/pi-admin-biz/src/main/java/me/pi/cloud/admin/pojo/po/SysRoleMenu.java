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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZnPi
 * @date 2022-09-26
 */
@Data
@NoArgsConstructor
public class SysRoleMenu {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色 ID
     */
    private Long roleId;
    /**
     * 菜单 ID
     */
    private Long menuId;

    public SysRoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
