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

package me.cloud.pi.gateway.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.cloud.pi.gateway.enums.ResponseStatus;

import java.time.LocalDateTime;

/**
 * 响应消息体
 *
 * @author ZnPi
 * @date 2022-10-21
 */
@Data
public class ResponseData<T> {
    private String code;
    private String msg;
    private Boolean success;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();
    private T data;

    private ResponseData(){}

    public static <T> ResponseData<T> ok(){
        return createResponseData(ResponseStatus.OK.getCode(), ResponseStatus.OK.getMsg(), null);
    }

    public static <T> ResponseData<T> ok(T data){
        return createResponseData(ResponseStatus.OK.getCode(), ResponseStatus.OK.getMsg(), data);
    }

    public static <T> ResponseData<T> createResponseData(String code, String msg, T data){
        return createResponseData(code, msg, true, data);
    }

    public static <T> ResponseData<T> error(){
        return createResponseData(
                ResponseStatus.SYS_ERROR.getCode(), ResponseStatus.SYS_ERROR.getMsg(), false,  null);
    }

    public static <T> ResponseData<T> error(String msg){
        return createResponseData(ResponseStatus.SYS_ERROR.getCode(), msg, false, null);
    }

    public static <T> ResponseData<T> error(String code, String msg){
        return createResponseData(code, msg, false, null);
    }

    public static <T> ResponseData<T> error(ResponseStatus status){
        return  createResponseData(status.getCode(), status.getMsg(), false, null);
    }

    public static <T> ResponseData<T> error(ResponseStatus status, String msg){
        return  createResponseData(status.getCode(), msg, false, null);
    }

    public static <T> ResponseData<T> createResponseData(String code, String msg, Boolean success, T data){
        ResponseData<T> rd = new ResponseData<>();
        rd.code = code;
        rd.msg = msg;
        rd.success = success;
        rd.data = data;
        return rd;
    }
}