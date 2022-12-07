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

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户导出 VO
 * @author ZnPi
 * @date 2022-09-14
 */
@Data
@ColumnWidth(20)
public class UserExportVO implements Serializable {
    private static final long serialVersionUID = -8934681702690528999L;

    /**
     * 用户名
     */
    @ExcelProperty("用户名")
    private String username;
    /**
     * 昵称
     */
    @ExcelProperty("昵称")
    private String nickname;
    /**
     * 手机
     */
    @ExcelProperty("手机")
    private String phone;
    /**
     * 部门
     */
    @ExcelProperty("部门")
    private String deptName;
}
