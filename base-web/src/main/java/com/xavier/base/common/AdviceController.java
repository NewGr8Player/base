package com.xavier.base.common;

import com.xavier.base.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class AdviceController extends BaseController {

    private static final String COMMON_ERROR_PAGE = "/status/error";

    /**
     * 捕捉shiro的异常
     *
     * @param e ShiroException
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ModelAndView handle401(ShiroException e) {
        log.error("Shiro异常。", e);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("code", HttpStatus.UNAUTHORIZED.value());
        dataMap.put("reason", e.getMessage());
        return modelAndView(COMMON_ERROR_PAGE, dataMap);
    }

    /**
     * 捕捉UnauthorizedException
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView handle401(UnauthorizedException e) {
        log.error("鉴权异常。", e);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("code", HttpStatus.UNAUTHORIZED.value());
        dataMap.put("reason", e.getMessage());
        return modelAndView(COMMON_ERROR_PAGE, dataMap);
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
    public ModelAndView globalException(HttpServletRequest request, Throwable ex) {
        log.error("未知异常。",ex);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("code", getStatus(request));
        dataMap.put("reason", ex.getMessage());
        return modelAndView(COMMON_ERROR_PAGE, dataMap);
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
