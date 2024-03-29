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

package me.pi.cloud.gateway.exception;

import me.pi.cloud.gateway.enums.ResponseStatusEnum;
/**
 * 验证码不正确异常
 * @author ZnPi
 * @date 2022-08-27
 */
public class CaptchaIncorrectException extends RuntimeException{
    public CaptchaIncorrectException(ResponseStatusEnum responseStatus) {
        super(responseStatus.getMsg());
    }
}
