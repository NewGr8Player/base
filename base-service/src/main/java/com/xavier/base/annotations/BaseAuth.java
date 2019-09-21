package com.xavier.base.annotations;

import com.xavier.base.enums.AuthTypeEnum;

import java.lang.annotation.*;

/**
 * 权限认证注解
 *
 * @author NewGr8Player
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseAuth {

    /**
     * 权限认证类型
     *
     * @see AuthTypeEnum
     */
    AuthTypeEnum auth() default AuthTypeEnum.OPEN;
}
