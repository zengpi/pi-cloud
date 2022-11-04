package me.cloud.pi.gateway.exception;

import me.cloud.pi.gateway.enums.ResponseStatus;

/**
 * 验证码不正确异常
 * @author ZnPi
 * @date 2022-08-27
 */
public class CaptchaIncorrectException extends RuntimeException{
    public CaptchaIncorrectException(ResponseStatus responseStatus) {
        super(responseStatus.getMsg());
    }
}
