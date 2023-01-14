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

import java.io.Serial;

/**
 * 验证码空异常
 * @author ZnPi
 * @date 2022-08-27
 */
public class CaptchaEmptyException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 5136662834199205845L;

    public CaptchaEmptyException(ResponseStatusEnum responseStatus) {
        super(responseStatus.getMsg());
    }
}
