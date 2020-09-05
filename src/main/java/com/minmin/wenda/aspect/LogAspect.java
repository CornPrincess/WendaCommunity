package com.minmin.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author corn
 * @version 1.0.0
 */

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.minmin.wenda.controller.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for(Object arg : joinPoint.getArgs())
            if(arg != null) {
                sb.append("arg:" + arg.toString() + "|");
            }
        log.info("before method: " + sb.toString() + new Date());
    }

    @After("execution(* com.minmin.wenda.controller.IndexController.*(..))")
    public void afterMethod() {
        log.info("after method " + new Date());
    }

    @After("execution(* com.minmin.wenda.controller.*.*(..))")
    public void afterMethod2() {
        log.info("after method " + new Date());
    }

}
