package me.pi.cloud.auth.extension;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import me.pi.cloud.auth.exception.PiOAuth2AuthencticationException;
import me.pi.cloud.common.web.enums.ResponseStatusEnum;
import me.pi.cloud.common.web.util.HttpEndpointUtils;
import me.pi.cloud.common.web.util.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final String AUTHENTICATION_METHOD = "authentication_method";
    private static final String CREDENTIALS = "credentials";

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) {
        ResponseData<?> error = createError(exception);

        ServletServerHttpResponse servletServerHttpResponse = new ServletServerHttpResponse(response);
        servletServerHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        HttpEndpointUtils.writeWithMessageConverters(error, servletServerHttpResponse);
    }

    /**
     * 创建 error
     *
     * @param exception /
     * @return /
     */
    private ResponseData<?> createError(AuthenticationException exception) {
        ResponseData<?> error = null;

        if (exception instanceof PiOAuth2AuthencticationException) {
            error = ResponseData.error(((PiOAuth2AuthencticationException) exception).getCode(),
                    exception.getMessage());
        } else if (exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {

            String errorCode = oAuth2AuthenticationException.getError().getErrorCode();
            String description = oAuth2AuthenticationException.getError().getDescription();

            switch (errorCode) {
                case OAuth2ErrorCodes.INVALID_CLIENT:
                    if (StrUtil.isBlank(description)) {
                        error = ResponseData.error(ResponseStatusEnum.INVALID_GRANT, "无效的客户端");
                    } else if (description.contains(OAuth2ParameterNames.CLIENT_ID)) {
                        error = ResponseData.error(ResponseStatusEnum.CLIENT_NOT_EXIST);
                    } else if (description.contains(AUTHENTICATION_METHOD)) {
                        error = ResponseData.error(ResponseStatusEnum.AUTHORIZATION_DENIED);
                    } else if (description.contains(CREDENTIALS)) {
                        error = ResponseData.error(ResponseStatusEnum.CLIENT_PASSWORD_EMPTY);
                    } else if (description.contains(OAuth2ParameterNames.CLIENT_SECRET)) {
                        error = ResponseData.error(ResponseStatusEnum.CLIENT_PASSWORD_INCORRECT);
                    }
                    break;
                case OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE:
                    error = ResponseData.error(ResponseStatusEnum.UNSUPPORTED_GRANT_TYPE);
                    break;
                case OAuth2ErrorCodes.INVALID_REQUEST:
                    if (StrUtil.isBlank(description)) {
                        error = ResponseData.error(ResponseStatusEnum.INVALID_GRANT, "无效的客户端");
                    } else if (description.contains(OAuth2ParameterNames.GRANT_TYPE)) {
                        error = ResponseData.error(ResponseStatusEnum.GRANT_TYPE_EMPTY);
                    }
                    break;
                case OAuth2ErrorCodes.INVALID_GRANT:
                    error = ResponseData.error(ResponseStatusEnum.INVALID_GRANT);
                    break;
                case OAuth2ErrorCodes.INVALID_SCOPE:
                    error = ResponseData.error(ResponseStatusEnum.INVALID_SCOPE);
                    break;
                case OAuth2ErrorCodes.UNAUTHORIZED_CLIENT:
                    error = ResponseData.error(ResponseStatusEnum.UNAUTHORIZED_CLIENT);
                    break;
                default:
                    error = ResponseData.error(ResponseStatusEnum.USER_LOGIN_ABNORMAL.getCode(),
                            oAuth2AuthenticationException.getError().getErrorCode());
                    break;
            }
        } else {
            error = ResponseData.error(ResponseStatusEnum.USER_LOGIN_ABNORMAL, exception.getLocalizedMessage());
        }

        return error;
    }
}