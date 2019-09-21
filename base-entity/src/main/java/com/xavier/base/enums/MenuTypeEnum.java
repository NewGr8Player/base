package com.xavier.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.xavier.base.exception.UnknownEnumException;

/**
 * 菜单类型枚举
 *
 * @author NewGr8Player
 */
public enum MenuTypeEnum implements IEnum<Integer> {

    /**
     * 目录
     */
    CATALOG(1),
    /**
     * 菜单
     */
    MENU(2),
    /**
     * 按钮
     */
    BUTTON(3);

    @EnumValue
    private final int value;

    MenuTypeEnum(final int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }


    public static MenuTypeEnum getEnum(int value) {
        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.getValue() == value) {
                return menuTypeEnum;
            }
        }
        throw new UnknownEnumException("Error: Invalid MenuTypeEnum type value: " + value);
    }

}
