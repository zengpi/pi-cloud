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

package me.cloud.pi.auth.extension;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.cloud.pi.admin.api.dto.LogDTO;
import me.cloud.pi.auth.constant.SecurityConstants;
import me.cloud.pi.common.logging.event.LogEvent;
import me.cloud.pi.common.logging.util.LogUtils;
import me.cloud.pi.common.web.util.HttpEndpointUtils;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
@Component
public class PiAuthenticationSuccessHandler implements AuthenticationSuccessHandler, ApplicationEventPublisherAware {
    /**
     * ??????????????????????????? ResponseData ?????????????????????
     * Swagger ???????????????
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
        logDTO.setTitle("????????????");
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