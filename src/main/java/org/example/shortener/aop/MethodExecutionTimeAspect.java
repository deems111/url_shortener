package org.example.shortener.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method execution time
 */
@Aspect
@Component
@Slf4j
public class MethodExecutionTimeAspect {

    @Around("@annotation(org.example.shortener.aop.annotation.LogExecutionTime)")
    public Object logAroundCreateEmployee(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        var timeBefore = System.currentTimeMillis();
        var proceed = joinPoint.proceed();
        var timeAfter = System.currentTimeMillis();
        log.info("Method - {}, execution time - {} ms", methodName, timeAfter - timeBefore);
        return proceed;
    }

}
