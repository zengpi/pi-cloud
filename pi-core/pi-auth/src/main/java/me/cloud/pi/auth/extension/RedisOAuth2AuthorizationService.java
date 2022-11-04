package me.cloud.pi.auth.extension;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * An {@link OAuth2AuthorizationService} that stores {@link OAuth2Authorization}'s in-redis.
 * @author ZnPi
 * @date 2022-10-22
 */
@Component
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {
    private static final String AUTHORIZATION = "token";
    private final static Long TIMEOUT = 10L;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");

        // state
        String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (Objects.nonNull(state)) {
            redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.STATE, state), authorization, TIMEOUT,
                    TimeUnit.MINUTES);
        }

        // access token
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (Objects.nonNull(accessToken)) {
            OAuth2AccessToken token = accessToken.getToken();
            long timeout = ChronoUnit.SECONDS.between(Objects.requireNonNull(token.getIssuedAt()), token.getExpiresAt());
            String key = buildKey(OAuth2ParameterNames.ACCESS_TOKEN, token.getTokenValue());
            redisTemplate.opsForValue().set(key, authorization, timeout, TimeUnit.SECONDS);
        }

        // refresh token
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            OAuth2RefreshToken token = refreshToken.getToken();
            long timeout = ChronoUnit.SECONDS.between(Objects.requireNonNull(token.getIssuedAt()), token.getExpiresAt());
            String key = buildKey(OAuth2ParameterNames.REFRESH_TOKEN, token.getTokenValue());
            redisTemplate.opsForValue().set(key, authorization, timeout, TimeUnit.SECONDS);
        }

        // code
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                .getToken(OAuth2AuthorizationCode.class);
        if (Objects.nonNull(authorizationCode)) {
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            long timeout = ChronoUnit.MINUTES.between(Objects.requireNonNull(authorizationCodeToken.getIssuedAt()),
                    authorizationCodeToken.getExpiresAt());
            redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
                    authorization, timeout, TimeUnit.MINUTES);
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");

        ArrayList<String> keys = new ArrayList<>();

        // state
        String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (Objects.nonNull(state)) {
            keys.add(buildKey(OAuth2ParameterNames.STATE, state));
        }

        // access token
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (Objects.nonNull(accessToken)) {
            OAuth2AccessToken token = accessToken.getToken();
            keys.add(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, token.getTokenValue()));
        }

        // refresh token
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            OAuth2RefreshToken token = refreshToken.getToken();
            keys.add(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, token.getTokenValue()));
        }

        // code
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                .getToken(OAuth2AuthorizationCode.class);
        if (Objects.nonNull(authorizationCode)) {
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            keys.add(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()));
        }

        redisTemplate.delete(keys);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        if(tokenType == null) {
            tokenType = OAuth2TokenType.ACCESS_TOKEN;
        }
        return (OAuth2Authorization) redisTemplate.opsForValue().get(buildKey(tokenType.getValue(), token));
    }

    private String buildKey(String type, String id) {
        return String.format("%s:%s:%s", AUTHORIZATION, type, id);
    }
}