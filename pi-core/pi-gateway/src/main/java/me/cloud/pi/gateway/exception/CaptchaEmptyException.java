package me.cloud.pi.gateway.exception;

import me.cloud.pi.gateway.enums.ResponseStatus;

/**
 * 验证码空异常
 * @author ZnPi
 * @date 2022-08-27
 */
public class CaptchaEmptyException extends RuntimeException{
    private static final long serialVersionUID = 5136662834199205845L;

    public CaptchaEmptyException(ResponseStatus responseStatus) {
        super(responseStatus.getMsg());
    }
}
