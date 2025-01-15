package com.pm.projectmanager.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger exceptionLogger = LoggerFactory.getLogger("EXCEPTION_LOGGER");
    private static final Logger applicationLogger = LoggerFactory.getLogger("APPLICATION_LOGGER");

    @AfterThrowing(pointcut = "execution(* com.pm.projectmanager.domain..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        exceptionLogger.error("{}.{} [{}]: {}", className, methodName, MDC.get("requestId"),ex.getMessage());
    }

    @Pointcut("execution(* com.pm.projectmanager.domain..*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        applicationLogger.debug("{}.{} [{}] [{}ms]", className, methodName, MDC.get("requestId"), elapsedTime);
        return result;
    }
}