package com.xavier.base.exception;

/**
 * 全局未验证异常
 *
 * @author NewGr8Player
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
