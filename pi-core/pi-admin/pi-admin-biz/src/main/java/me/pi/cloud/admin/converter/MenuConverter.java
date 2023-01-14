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


import me.pi.cloud.admin.pojo.dto.MenuDTO;
import me.pi.cloud.admin.pojo.po.SysMenu;
import me.pi.cloud.admin.pojo.vo.CurrentUserMenuTreeVO;
import me.pi.cloud.admin.pojo.vo.MenuTreeVO;
import me.pi.cloud.common.web.vo.SelectTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author ZnPi
 * @date 2022-09-22
 */
@Mapper(componentModel = "spring", uses = VisibleToHiddenFormat.class)
public interface MenuConverter {
    /**
     * MenuDTO -> SysMenu
     *
     * @param dto MenuDTO
     * @return SysMenu
     */
    SysMenu menuDtoToSysMenu(MenuDTO dto);

    /**
     * SysMenu -> MenuTreeVO
     *
     * @param menu SysMenu
     * @return MenuTreeVO
     */
    MenuTreeVO sysMenuToMenuTreeVo(SysMenu menu);

    /**
     * SysMenu -> CurrentUserMenuTreeVO
     *
     * @param menu SysMenu
     * @return CurrentUserMenuTreeVO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "meta.title", source = "name")
    @Mapping(target = "meta.type", source = "type")
    @Mapping(target = "meta.icon", source = "icon")
    @Mapping(target = "meta.hidden", source = "visible")
    @Mapping(target = "meta.keepAlive", source = "type")
    CurrentUserMenuTreeVO sysMenuToCurrentUserMenuTreeVo(SysMenu menu);

    /**
     * SysMenu -> SelectTreeVO
     *
     * @param menu SysMenu
     * @return SelectTreeVO
     */
    @Mapping(source = "id", target = "value")
    @Mapping(source = "name", target = "label")
    @Mapping(target = "children", ignore = true)
    SelectTreeVO sysMenuToSelectTreeVo(SysMenu menu);

}
