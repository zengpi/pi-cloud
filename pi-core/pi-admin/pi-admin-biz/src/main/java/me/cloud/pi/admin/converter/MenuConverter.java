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


import me.cloud.pi.admin.pojo.dto.MenuDTO;
import me.cloud.pi.admin.pojo.po.SysMenu;
import me.cloud.pi.admin.pojo.vo.MenuVO;
import me.cloud.pi.common.web.pojo.vo.SelectTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-22
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {
    /**
     * menu PO -> VO
     *
     * @param menu /
     * @return /
     */
    @Mappings({
            @Mapping(source = "externalLinks", target = "externalLinks"),
            @Mapping(source = "createTime", target = "createTime")
    })
    MenuVO menuPoToMenuVo(SysMenu menu);

    /**
     * po 转 vo
     *
     * @param menus 菜单
     * @return /
     */
    List<MenuVO> menusPoToMenusVo(List<SysMenu> menus);

    /**
     * po -> selectTree
     *
     * @param menu /
     * @return /
     */
    @Mappings({
            @Mapping(source = "id", target = "value"),
            @Mapping(source = "name", target = "label")
    })
    SelectTreeVO menuPoToSelectTreeVo(SysMenu menu);

    /**
     * dto -> po
     * @param dto /
     * @return /
     */
    SysMenu menuDtoToMenuPo(MenuDTO dto);
}
