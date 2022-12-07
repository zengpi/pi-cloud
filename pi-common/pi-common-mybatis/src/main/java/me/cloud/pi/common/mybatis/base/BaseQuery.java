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

package me.cloud.pi.common.mybatis.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Getter
@Setter
@Schema(description = "基础查询参数")
public class BaseQuery {
    /**
     * 页码
     */
    @Schema(description = "页码")
    private Integer pageNum = 1;

    /**
     * 每页记录数
     */
    @Schema(description = "每页记录数")
    private Integer pageSize = 20;

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    private String keyWord;
}
