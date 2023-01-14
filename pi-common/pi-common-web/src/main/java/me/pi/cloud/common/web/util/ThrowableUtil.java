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

package me.pi.cloud.common.web.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具
 *
 * @author ZnPi
 * @date 2022-08-16
 */
public class ThrowableUtil {
    /**
     * 获取堆栈信息
     * @param throwable /
     * @return 堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        try(PrintWriter printWriter = new PrintWriter(stringWriter)){
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        }
    }
}
