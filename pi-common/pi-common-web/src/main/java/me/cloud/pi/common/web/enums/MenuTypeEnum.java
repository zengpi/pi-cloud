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
