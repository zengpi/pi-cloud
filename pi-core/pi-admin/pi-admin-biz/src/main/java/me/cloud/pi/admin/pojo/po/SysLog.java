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

package me.cloud.pi.admin.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cloud.pi.common.mybatis.base.BaseEntity;

/**
 * @author ZnPi
 * @date 2022-11-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseEntity {
    /**
     * 类型(0:=异常;1:=正常)
     */
    private Integer type;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String exceptionDesc;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 方法名称
     */
    private String methodName;
}
