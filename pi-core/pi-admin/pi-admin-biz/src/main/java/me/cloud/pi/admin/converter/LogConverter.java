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

package me.cloud.pi.admin.converter;

import me.cloud.pi.admin.api.dto.LogDTO;
import me.cloud.pi.admin.pojo.po.SysLog;
import me.cloud.pi.admin.pojo.vo.LogVO;
import me.cloud.pi.common.mybatis.util.PiPage;
import org.mapstruct.Mapper;

/**
 * @author ZnPi
 * @date 2022-11-19
 */
@Mapper(componentModel = "spring")
public interface LogConverter {
    /**
     * po -> dto
     * @param dto /
     * @return /
     */
    SysLog dtoToPo(LogDTO dto);

    /**
     * pagePO -> pageVO
     * @param log /
     * @return /
     */
    PiPage<LogVO> pagePoToPageVo(PiPage<SysLog> log);
}
