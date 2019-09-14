package com.xavier.base.common;

import com.xavier.base.entity.ResponseEntity;
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
    public ResponseEntity handle401(ShiroException e) {
        log.error("Shiro异常。", e);
        return new ResponseEntity(401, e.getMessage(), null);
    }

    /**
     * 捕捉UnauthorizedException
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handle401(UnauthorizedException e) {
        log.error("鉴权异常。", e);
        return new ResponseEntity(401, "Unauthorized", null);
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
    public ResponseEntity globalException(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity(getStatus(request).value(), ex.getMessage(), null);
    }

    /**
     * 获取HTTP请求状态
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
