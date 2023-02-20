package org.example.shortener.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * Aspect for logging exceptions and warnings
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @AfterThrowing(pointcut = "@annotation(org.example.shortener.aop.annotation.LogException)", throwing = "ex")
    public Object logExecutionTime(JoinPoint joinPoint, Throwable ex) throws Throwable {
        log.error("Exception occurred - " + ex.getMessage());
        log.error("Exception occurred - {} with cause = {}",
                joinPoint.getSignature().getName(), ex.getCause() != null ? ex.getCause() : "Undefined Error");
        throw ex;
    }
}
