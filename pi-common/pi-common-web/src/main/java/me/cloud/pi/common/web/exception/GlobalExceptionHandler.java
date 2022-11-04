package me.cloud.pi.common.web.exception;

import lombok.extern.slf4j.Slf4j;
import me.cloud.pi.common.web.enums.ResponseStatus;
import me.cloud.pi.common.web.util.ResponseData;
import me.cloud.pi.common.web.util.ThrowableUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常类
 * @author ZnPi
 * @date 2022-11-04
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 业务异常
     * @param e /
     * @return /
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseData<Object> badRequestException(BadRequestException e){
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseData.error(e.getCode(), e.getMessage());
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