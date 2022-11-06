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

package me.cloud.pi.common.web.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ZnPi
 * @date 2022-08-20
 */
@Getter
@RequiredArgsConstructor
public enum MenuTypeEnum {
    /**
     * 目录
     */
    DIR(1, "directory"),

    /**
     * 菜单
     */
    MENU(2, "menu"),

    /**
     * 按钮
     */
    BUTTON(3, "button");


    /**
     * 菜单类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;
}
