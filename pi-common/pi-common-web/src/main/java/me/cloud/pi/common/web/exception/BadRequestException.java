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

package me.cloud.pi.common.web.exception;

import lombok.Getter;
import me.cloud.pi.common.web.enums.ResponseStatus;

/**
 * @author ZnPi
 * @date 2022-08-16
 */
@Getter
public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = -1528006679211572586L;

    private String code = ResponseStatus.REQUEST_PARAM_ERROR.getCode();

    public BadRequestException(String msg){
        super(msg);
    }

    public BadRequestException(ResponseStatus responseStatus){
        super(responseStatus.getMsg());
        this.code = responseStatus.getCode();
    }

    public BadRequestException(ResponseStatus responseStatus, String msg){
        super(msg);
        this.code = responseStatus.getCode();
    }
}