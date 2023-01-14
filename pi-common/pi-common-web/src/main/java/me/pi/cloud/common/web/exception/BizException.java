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

package me.pi.cloud.common.web.exception;

import lombok.Getter;
import me.pi.cloud.common.web.enums.ResponseStatusEnum;

import java.io.Serial;

/**
 * @author ZnPi
 * @date 2022-12-02 22:01
 */
@Getter
public class BizException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1906310822616623742L;

    private String code = ResponseStatusEnum.BIZ_EXCEPTION.getCode();

    public BizException(String msg){
        super(msg);
    }

    public BizException(ResponseStatusEnum responseStatus){
        super(responseStatus.getMsg());
        this.code = responseStatus.getCode();
    }

    public BizException(ResponseStatusEnum responseStatus, String msg){
        super(msg);
        this.code = responseStatus.getCode();
    }
}
