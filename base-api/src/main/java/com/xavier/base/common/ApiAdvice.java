package com.xavier.base.common;

import com.xavier.base.common.responses.ApiResponses;
import com.xavier.base.enums.ErrorCodeEnum;
import com.xavier.base.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ApiAdvice {

    /**
     * 捕捉shiro的异常
     *
     * @param e ShiroException
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ApiResponses handle401(ShiroException e) {
        log.error("Shiro异常。", e);
        return ApiResponses.failure(ErrorCodeEnum.UNAUTHORIZED.convert(), e);
    }

    /**
     * 捕捉UnauthorizedException
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponses handle401(UnauthorizedException e) {
        log.error("鉴权异常。", e);
        return ApiResponses.failure(ErrorCodeEnum.UNAUTHORIZED.convert(), e);
    }

    /**
     * 全局异常捕获
     *
     * @param request 请求
     * @param ex      异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponses globalThrowable(HttpServletRequest request, Throwable ex) {
        log.error("系统异常！", ex);
        return ApiResponses.failure(ErrorCodeEnum.INTERNAL_SERVER_ERROR.convert(), (Exception) ex);
    }
}
