package com.xavier.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.xavier.base.exception.UnknownEnumException;


/**
 * 权限类型枚举
 *
 * @author NewGr8Player
 */
public enum AuthTypeEnum implements IEnum<Integer> {

    /**
     * 需要登录
     */
    LOGIN(1),
    /**
     * 开放,无需鉴权
     */
    OPEN(2),
    /**
     * 需要鉴定是否包含权限
     */
    AUTH(3);

    @EnumValue
    private final int value;

    AuthTypeEnum(final int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }


    public static AuthTypeEnum getEnum(int value) {
        for (AuthTypeEnum menuTypeEnum : AuthTypeEnum.values()) {
            if (menuTypeEnum.getValue() == value) {
                return menuTypeEnum;
            }
        }
        throw new UnknownEnumException("Error: Invalid AuthTypeEnum type value: " + value);
    }

}
