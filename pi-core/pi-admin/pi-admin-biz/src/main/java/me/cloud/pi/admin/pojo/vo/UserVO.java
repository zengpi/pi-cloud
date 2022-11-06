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

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Data
public class UserVO implements Serializable {
    public static final long serialVersionUID = -1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 手机
     */
    private String phone;
    /**
     * 部门 ID
     */
    private Integer deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 角色 ID 列表，多个以逗号分隔
     */
    private List<Long> roleIds;
    /**
     * 状态（0:=禁用，1:=启用）
     */
    private Integer enabled;
    /**
     * 创建时间
     */
    private Date createTime;
}
