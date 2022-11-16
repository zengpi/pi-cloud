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

import cn.hutool.core.util.StrUtil;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * 自定义 JdbcRegisteredClientRepository
 * @author ZnPi
 * @date 2022-11-14
 */
public class PiJdbcRegisteredClientRepository implements RegisteredClientRepository {
    // @formatter:off
    /**
     * RegisteredClient 列名
     */
    private static final String COLUMN_NAMES = "id, "
            + "client_id, "
            + "create_time, "
            + "client_secret, "
            + "client_secret_expires_at, "
            + "client_name, "
            + "client_authentication_methods, "
            + "authorization_grant_types, "
            + "redirect_uris, "
            + "scopes, "
            + "require_authorization_consent, "
            + "access_token_time_to_live, "
            + "access_token_format, "
            + "refresh_token_time_to_live";
    // @formatter:on

    /**
     * 表名
     */
    private static final String TABLE_NAME = "sys_registered_client";

    private static final String LOAD_REGISTERED_CLIENT_SQL = "SELECT " + COLUMN_NAMES + " FROM " + TABLE_NAME + " WHERE ";

    private final JdbcOperations jdbcOperations;
    private final RowMapper<RegisteredClient> registeredClientRowMapper;

    /**
     * Constructs a {@code PiJdbcRegisteredClientRepository} using the provided parameters.
     *
     * @param jdbcOperations the JDBC operations
     */
    public PiJdbcRegisteredClientRepository(JdbcOperations jdbcOperations) {
        Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
        this.jdbcOperations = jdbcOperations;
        this.registeredClientRowMapper = new PiJdbcRegisteredClientRepository.RegisteredClientRowMapper();
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return findBy("id = ? AND deleted = 0", id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return findBy("client_id = ? AND deleted = 0", clientId);
    }

    private RegisteredClient findBy(String filter, Object... args) {
        List<RegisteredClient> result = this.jdbcOperations.query(
                LOAD_REGISTERED_CLIENT_SQL + filter, this.registeredClientRowMapper, args);
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * The default {@link RowMapper} that maps the current row in
     * {@code java.sql.ResultSet} to {@link RegisteredClient}.
     */
    public static class RegisteredClientRowMapper implements RowMapper<RegisteredClient> {
        @Override
        public RegisteredClient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Timestamp clientIdIssuedAt = rs.getTimestamp("create_time");
            Timestamp clientSecretExpiresAt = rs.getTimestamp("client_secret_expires_at");
            Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(rs.getString("client_authentication_methods"));
            Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(rs.getString("authorization_grant_types"));
            Set<String> redirectUris = StringUtils.commaDelimitedListToSet(rs.getString("redirect_uris"));
            Set<String> clientScopes = StringUtils.commaDelimitedListToSet(rs.getString("scopes"));
            boolean requireAuthorizationConsent = rs.getInt("require_authorization_consent") == 1;
            int accessTokenTimeToLive = rs.getInt("access_token_time_to_live");
            String accessTokenFormat = rs.getString("access_token_format");
            int refreshTokenTimeToLive = rs.getInt("refresh_token_time_to_live");

            // @formatter:off
            RegisteredClient.Builder builder = RegisteredClient.withId(rs.getString("id"))
                    .clientId(rs.getString("client_id"))
                    .clientIdIssuedAt(clientIdIssuedAt != null ? clientIdIssuedAt.toInstant() : null)
                    .clientSecret(rs.getString("client_secret"))
                    .clientSecretExpiresAt(clientSecretExpiresAt != null ? clientSecretExpiresAt.toInstant() : null)
                    .clientName(rs.getString("client_name"))
                    .clientAuthenticationMethods((authenticationMethods) ->
                            clientAuthenticationMethods.forEach(authenticationMethod ->
                                    authenticationMethods.add(resolveClientAuthenticationMethod(authenticationMethod))))
                    .authorizationGrantTypes((grantTypes) ->
                            authorizationGrantTypes.forEach(grantType ->
                                    grantTypes.add(resolveAuthorizationGrantType(grantType))))
                    .redirectUris((uris) -> uris.addAll(redirectUris))
                    .scopes((scopes) -> scopes.addAll(clientScopes));
            // @formatter:on

            builder.clientSettings(ClientSettings
                    .builder()
                    .requireAuthorizationConsent(requireAuthorizationConsent)
                    .build());

            TokenSettings.Builder tokenSettingsBuilder = TokenSettings
                    .builder()
                    .accessTokenTimeToLive(Duration.ofSeconds(accessTokenTimeToLive))
                    .refreshTokenTimeToLive(Duration.ofSeconds(refreshTokenTimeToLive));

            if (StrUtil.isBlank(accessTokenFormat)) {
                tokenSettingsBuilder.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED);
            }else{
                tokenSettingsBuilder.accessTokenFormat(new OAuth2TokenFormat(accessTokenFormat));
            }

            builder.tokenSettings(tokenSettingsBuilder.build());

            return builder.build();
        }

        private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
            if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
                return AuthorizationGrantType.AUTHORIZATION_CODE;
            } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
                return AuthorizationGrantType.CLIENT_CREDENTIALS;
            } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
                return AuthorizationGrantType.REFRESH_TOKEN;
            }
            // Custom authorization grant type
            return new AuthorizationGrantType(authorizationGrantType);
        }

        private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod) {
            if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
                return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
            } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
                return ClientAuthenticationMethod.CLIENT_SECRET_POST;
            } else if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
                return ClientAuthenticationMethod.NONE;
            }
            // Custom client authentication method
            return new ClientAuthenticationMethod(clientAuthenticationMethod);
        }
    }
}
