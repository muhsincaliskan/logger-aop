package com.muhsincaliskan.loggeraop.aspect;

import com.muhsincaliskan.loggeraop.util.ErrorInfoUtil;
import com.muhsincaliskan.loggeraop.util.LoggingInfoUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Around("@annotation(com.muhsincaliskan.loggeraop.EnableLogger)")
    public Object logMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {


        final long startTimeMillis = System.currentTimeMillis();
        String methodName = proceedingJoinPoint.getSignature().getName();

        logger.info(LoggingInfoUtil.getBeforeLogMethod(proceedingJoinPoint, methodName));
        logger.info(LoggingInfoUtil.getAfterLogReturnedValue(proceedingJoinPoint, methodName));
        logger.info(LoggingInfoUtil.getAfterLogExecuteDuration(startTimeMillis, methodName));
        return proceedingJoinPoint.proceed();
    }


    @AfterThrowing
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable){
        logger.warn(ErrorInfoUtil.getAfterThrowingMessage(joinPoint, throwable));
    }

}
