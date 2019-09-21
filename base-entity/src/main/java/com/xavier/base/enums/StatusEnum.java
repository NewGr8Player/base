package com.xavier.base.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 状态枚举
 *
 * @author NewGr8Player
 */
public enum StatusEnum implements IEnum {

    NORMAL(0), DISABLE(1);

    @EnumValue
    private final int value;

    StatusEnum(final int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

}
