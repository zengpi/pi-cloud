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


import me.pi.cloud.admin.pojo.dto.DeptDTO;
import me.pi.cloud.admin.pojo.po.SysDept;
import me.pi.cloud.admin.pojo.vo.DeptTreeVO;
import me.pi.cloud.common.web.vo.SelectTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {
    /**
     * SysDept -> DeptTreeVO
     *
     * @param sysDept SysDept
     * @return DeptTreeVO
     */
    @Mapping(target = "children", ignore = true)
    DeptTreeVO sysDeptToDeptTreeVO(SysDept sysDept);

    /**
     * DeptDTO -> SysDept
     *
     * @param dto DeptDTO
     * @return SysDept
     */
    SysDept deptDtoToSysDept(DeptDTO dto);

    /**
     * SysDept -> SelectTreeVO
     *
     * @param sysDept SysDept
     * @return SelectTreeVO
     */
    @Mapping(source = "id", target = "value")
    @Mapping(source = "name", target = "label")
    @Mapping(target = "children", ignore = true)
    SelectTreeVO sysDeptToSelectTreeVo(SysDept sysDept);
}
