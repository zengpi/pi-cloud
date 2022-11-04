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