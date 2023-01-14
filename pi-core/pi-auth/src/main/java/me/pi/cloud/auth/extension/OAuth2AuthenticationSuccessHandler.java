package me.pi.cloud.auth.extension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import me.pi.cloud.admin.api.dto.LogDTO;
import me.pi.cloud.auth.constant.SecurityConstants;
import me.pi.cloud.common.log.event.LogEvent;
import me.pi.cloud.common.log.util.LogUtils;
import me.pi.cloud.common.web.util.HttpEndpointUtils;
import me.pi.cloud.common.web.util.ResponseData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler, ApplicationEventPublisherAware {
    /**
     * 指定响应数据不使用 ResponseData 封装的参数名称
     * Swagger 登录时可用
     */
    private static final String NO_RESPONSE_DATA_PARAM_NAME = "no_response_data";

    private final Converter<OAuth2AccessTokenResponse, Map<String, Object>> accessTokenResponseParametersConverter =
            new DefaultOAuth2AccessTokenResponseMapConverter();

    private ApplicationEventPublisher publisher;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }
        OAuth2AccessTokenResponse accessTokenResponse = builder.build();
        Map<String, Object> tokenResponseParameters = this.accessTokenResponseParametersConverter.convert(accessTokenResponse);
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

        LogDTO logDTO = LogUtils.getDefaultLogDTO();
        logDTO.setTitle("用户登录");
        publisher.publishEvent(new LogEvent(logDTO, SecurityConstants.TOKEN_PREFIX + accessToken.getTokenValue()));

        boolean noResponseData = Boolean.parseBoolean(request.getParameter(NO_RESPONSE_DATA_PARAM_NAME));
        if (noResponseData) {
            HttpEndpointUtils.writeWithMessageConverters(tokenResponseParameters, httpResponse);
        } else {
            ResponseData<Map<String, Object>> responseData = ResponseData.ok(tokenResponseParameters);
            HttpEndpointUtils.writeWithMessageConverters(responseData, httpResponse);
        }
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}