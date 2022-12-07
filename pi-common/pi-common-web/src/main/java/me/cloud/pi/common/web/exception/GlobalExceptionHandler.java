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

import lombok.extern.slf4j.Slf4j;
import me.cloud.pi.common.web.enums.ResponseStatus;
import me.cloud.pi.common.web.util.ResponseData;
import me.cloud.pi.common.web.util.ThrowableUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常类
 * @author ZnPi
 * @date 2022-11-04
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException{
    private static final long serialVersionUID = -458561019236063090L;

    /**
     * 业务异常
     * @param e /
     * @return /
     */
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseData<Object> badRequestException(BadRequestException e){
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseData.error(e.getCode(), e.getMessage());
    }

    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseData<?> methodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(ThrowableUtil.getStackTrace(e));
        String msg = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return ResponseData.error(ResponseStatus.REQUEST_PARAM_ERROR, msg);
    }

    /**
     * 处理所有不可知异常
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseData<Object> unknownException(Throwable e){
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseData.error(ResponseStatus.SYS_ERROR, e.getMessage());
    }
}