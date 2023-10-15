package com.muhsincaliskan.loggeraop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Around("@annotation(com.muhsincaliskan.loggeraop.EnableLogger)")
    public Object logMethodAround(ProceedingJoinPoint pjp) throws Throwable {
        final long start = System.currentTimeMillis();
        String methodName= pjp.getSignature().getName();
        Map<String, Object> parameters= getParameters(pjp);
        logger.info("{} started with parameters : {}", methodName, parameters);
        Object proceed= pjp.proceed();
        final long executionTime = System.currentTimeMillis() - start;
        logger.info("{} finished with return value: {}", methodName, proceed);
        logger.info(pjp.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }


    @AfterThrowing
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable){
        logger.warn("{} failed with exception message: {}", joinPoint.getSignature().getName(),throwable.getCause() != null ? throwable.getCause() : "NULL");
    }
    private Map<String,Object> getParameters(ProceedingJoinPoint proceedingJoinPoint){
        Map<String, Object> parameters = new HashMap<>();
        String[] parameterNames = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = proceedingJoinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            parameters.put(parameterNames[i],parameterValues[i]);
        }
        return parameters;
    }
}
