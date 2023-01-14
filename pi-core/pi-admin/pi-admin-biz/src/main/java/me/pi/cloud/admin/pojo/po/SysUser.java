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

import java.util.Date;

/**
 * @author ZnPi
 * @date 2022-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date bday;
    /**
     * 手机
     */
    private String phone;
    /**
     * 部门 ID
     */
    private Long deptId;
    /**
     * 状态（0:=禁用，1:=启用）
     */
    private Integer enabled;
    /**
     * 是否删除（0:=未删除，1:=已删除）
     */
    private Integer deleted;
}
