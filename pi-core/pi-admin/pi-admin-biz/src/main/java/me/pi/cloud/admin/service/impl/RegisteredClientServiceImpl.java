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

package me.pi.cloud.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.pi.cloud.admin.converter.RegisteredClientConverter;
import me.pi.cloud.admin.mapper.RegisteredClientMapper;
import me.pi.cloud.admin.pojo.dto.RegisteredClientDTO;
import me.pi.cloud.admin.pojo.po.SysRegisteredClient;
import me.pi.cloud.admin.pojo.vo.RegisteredClientVO;
import me.pi.cloud.admin.service.RegisteredClientService;
import me.pi.cloud.common.mybatis.base.BaseQuery;
import me.pi.cloud.common.mybatis.util.PiPage;
import me.pi.cloud.common.security.constant.SecurityConstants;
import me.pi.cloud.common.web.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-11-14
 */
@Service
@RequiredArgsConstructor
public class RegisteredClientServiceImpl extends ServiceImpl<RegisteredClientMapper, SysRegisteredClient>
        implements RegisteredClientService {
    private final RegisteredClientConverter registeredClientConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PiPage<RegisteredClientVO> getClients(BaseQuery query) {
        PiPage<SysRegisteredClient> page = new PiPage<>(query.getPageNum(), query.getPageSize());
        PiPage<SysRegisteredClient> clients = super.page(page, Wrappers
                .lambdaQuery(SysRegisteredClient.class)
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysRegisteredClient::getClientId, query.getKeyWord())
                .or()
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysRegisteredClient::getClientName, query.getKeyWord())
                .select(SysRegisteredClient::getId, SysRegisteredClient::getClientId,
                        SysRegisteredClient::getClientSecretExpiresAt, SysRegisteredClient::getClientName,
                        SysRegisteredClient::getClientAuthenticationMethods, SysRegisteredClient::getAuthorizationGrantTypes,
                        SysRegisteredClient::getRedirectUris, SysRegisteredClient::getScopes,
                        SysRegisteredClient::getRequireAuthorizationConsent, SysRegisteredClient::getAccessTokenTimeToLive,
                        SysRegisteredClient::getAccessTokenFormat, SysRegisteredClient::getRefreshTokenTimeToLive)
        );
        return registeredClientConverter.pageSysClientToPageClientVo(clients);
    }

    @Override
    public void saveOrUpdate(RegisteredClientDTO dto) {
        if (dto.getId() == null) {
            long count = super.count(Wrappers
                    .lambdaQuery(SysRegisteredClient.class)
                    .eq(SysRegisteredClient::getClientId, dto.getClientId()));
            if (count > 0) {
                throw new BadRequestException("客户端 " + dto.getClientId() + " 已存在");
            }
            if (StrUtil.isBlank(dto.getClientSecret())) {
                dto.setClientSecret(passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD));
            }
        } else {
            if (StrUtil.isNotBlank(dto.getClientSecret())) {
                dto.setClientSecret(passwordEncoder.encode(dto.getClientSecret()));
            }
        }

        if (dto.getAuthorizationGrantTypes() != null &&
                dto.getAuthorizationGrantTypes().contains(AuthorizationGrantType.AUTHORIZATION_CODE.getValue())
                && StrUtil.isBlank(dto.getRedirectUris())) {
            throw new BadRequestException("当授权类型中含有授权码模式时，重定向URI不能为空");
        }
        super.saveOrUpdate(registeredClientConverter.clientDtoToSysClient(dto));
    }

    @Override
    public void delClient(String ids) {
        if (StrUtil.isBlank(ids)) {
            throw new BadRequestException("ids 不能为空");
        }

        Set<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        super.removeByIds(idSet);
    }
}
