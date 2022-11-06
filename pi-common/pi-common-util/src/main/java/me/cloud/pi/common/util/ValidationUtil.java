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

package me.cloud.pi.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZnPi
 * @date 2022-10-23
 */
public class ValidationUtil {
    public static final Pattern PHONE_PATTERN =
            Pattern.compile("^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");

    /**
     * 验证手机号码
     * @param phone 手机号
     * @return 当且仅当整个序列匹配此匹配器的模式时，返回 true
     */
    public static boolean checkPhone(String phone){
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
}