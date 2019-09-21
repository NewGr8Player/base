package com.xavier.base.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * Controller统一切点日志处理
 *
 * @author NewGr8Player
 */
@Slf4j
@Aspect
@Configuration
public class LogRecordAspect {


    //@Pointcut("execution(public * com.xavier.base.api.*Api.*(..))")
    public void pointCut() {
    }

    // TODO 访问日志
}
