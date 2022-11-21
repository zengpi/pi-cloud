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

package me.cloud.pi.admin.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cloud.pi.common.mybatis.base.BaseQueryParam;

import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMemberQueryParam extends BaseQueryParam {
    /**
     * 角色 ID
     */
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;
}
