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

import me.pi.cloud.admin.api.dto.LogDTO;
import me.pi.cloud.admin.pojo.po.SysLog;
import me.pi.cloud.admin.pojo.vo.LogVO;
import me.pi.cloud.common.mybatis.util.PiPage;
import org.mapstruct.Mapper;

/**
 * @author ZnPi
 * @date 2022-11-19
 */
@Mapper(componentModel = "spring")
public interface LogConverter {
    /**
     * LogDTO -> SysLog
     * @param dto LogDTO
     * @return SysLog
     */
    SysLog logDtoToSysLog(LogDTO dto);

    /**
     * PiPage<SysLog> -> PiPage<LogVO>
     * @param log PiPage<SysLog>
     * @return PiPage<LogVO>
     */
    PiPage<LogVO> pageSysLogToPageLogVo(PiPage<SysLog> log);
}
