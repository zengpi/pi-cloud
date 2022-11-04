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