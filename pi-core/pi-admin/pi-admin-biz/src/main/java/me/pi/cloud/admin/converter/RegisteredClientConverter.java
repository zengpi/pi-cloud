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

package me.pi.cloud.admin.converter;

import me.pi.cloud.admin.pojo.dto.RegisteredClientDTO;
import me.pi.cloud.admin.pojo.po.SysRegisteredClient;
import me.pi.cloud.admin.pojo.vo.RegisteredClientVO;
import me.pi.cloud.common.mybatis.util.PiPage;
import org.mapstruct.Mapper;

/**
 * @author ZnPi
 * @date 2022-11-14
 */
@Mapper(componentModel = "spring")
public interface RegisteredClientConverter {
    /**
     * PiPage<SysRegisteredClient> -> PiPage<RegisteredClientVO>
     *
     * @param pageClients PiPage<SysRegisteredClient>
     * @return PiPage<RegisteredClientVO>
     */
    PiPage<RegisteredClientVO> pageSysClientToPageClientVo(PiPage<SysRegisteredClient> pageClients);

    /**
     * RegisteredClientDTO -> SysRegisteredClient
     * @param dto RegisteredClientDTO
     * @return SysRegisteredClient
     */
    SysRegisteredClient clientDtoToSysClient(RegisteredClientDTO dto);
}
