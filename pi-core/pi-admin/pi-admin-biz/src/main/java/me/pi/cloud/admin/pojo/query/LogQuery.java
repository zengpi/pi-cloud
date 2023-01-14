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

package me.pi.cloud.admin.pojo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pi.cloud.common.mybatis.base.BaseQuery;

import java.time.LocalDateTime;

/**
 * @author ZnPi
 * @date 2022-11-20
 */
@Schema(title = "日志查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class LogQuery extends BaseQuery {
    @Schema(description = "查询字段")
    private String queryColumn;

    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

    @Schema(description = "类型")
    private Integer type;

    public enum LogColumn {
        /**
         * 创建时间
         */
        CREATE_BY("create_by"),
        /**
         * 标题
         */
        TITLE("title"),
        /**
         * 方法名称
         */
        METHOD_NAME("method_name");

        private String column;

        LogColumn(String column) {
            this.column = column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getColumn() {
            return this.column;
        }
    }
}
