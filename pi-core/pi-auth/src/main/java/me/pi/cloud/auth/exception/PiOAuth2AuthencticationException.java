package me.pi.cloud.auth.exception;

import lombok.Getter;
import me.pi.cloud.common.web.enums.ResponseStatusEnum;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
@Getter
public class PiOAuth2AuthencticationException extends OAuth2AuthenticationException {
    private final String code;

    public PiOAuth2AuthencticationException(ResponseStatusEnum status) {
        super(new OAuth2Error(status.getMsg()), status.getMsg());
        this.code = status.getCode();
    }

    public PiOAuth2AuthencticationException(ResponseStatusEnum status, String msg) {
        super(new OAuth2Error(status.getMsg()), msg);
        this.code = status.getCode();
    }
}