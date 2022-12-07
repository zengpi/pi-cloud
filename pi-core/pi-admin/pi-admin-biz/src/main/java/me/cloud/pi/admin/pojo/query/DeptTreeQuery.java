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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ZnPi
 * @date 2022-11-30 21:44
 */
@Data
@Schema(title = "获取部门（树形）查询参数")
public class DeptTreeQuery {
    @Schema(description = "关键字")
    private String keyWord;
}
