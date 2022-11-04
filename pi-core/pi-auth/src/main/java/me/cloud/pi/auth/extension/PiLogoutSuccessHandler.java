package me.cloud.pi.auth.extension;

import lombok.SneakyThrows;
import me.cloud.pi.common.web.util.HttpEndpointUtils;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZnPi
 * @date 2022-10-22
 */
public class PiLogoutSuccessHandler implements LogoutSuccessHandler {
    private final HttpStatus httpStatusToReturn;

    public PiLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        Assert.notNull(httpStatusToReturn, "The provided HttpStatus must not be null.");
        this.httpStatusToReturn = httpStatusToReturn;
    }

    public PiLogoutSuccessHandler() {
        this.httpStatusToReturn = HttpStatus.OK;
    }

    @Override
    @SneakyThrows
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setStatus(this.httpStatusToReturn.value());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        HttpEndpointUtils.writeWithMessageConverters(ResponseData.ok(), httpResponse);
    }
}