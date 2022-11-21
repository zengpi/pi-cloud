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
import java.util.Date;

/**
 * @author ZnPi
 * @date 2022-11-20
 */
@Data
@ColumnWidth(20)
public class LogExportVO implements Serializable {
    private static final long serialVersionUID = 4659360932423855447L;
    @ExcelProperty("时间")
    private Date createTime;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("IP地址")
    private String ip;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("请求方式")
    private String requestMethod;

    @ExcelProperty("请求时间")
    private String requestTime;

    @ExcelProperty("请求方法")
    private String methodName;

    @ExcelProperty("请求参数")
    private String requestParam;

    @ExcelProperty("异常描述")
    private String exceptionDesc;
}
