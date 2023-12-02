package com.muhsincaliskan.loggeraop.util;

import org.aspectj.lang.JoinPoint;

public class ErrorInfoUtil {

    private static final String AFTER_THROWING_MESSAGE = "%s failed with exception message: %s";

    public static String getAfterThrowingMessage(JoinPoint joinPoint, Throwable throwable) {
        return String.format(AFTER_THROWING_MESSAGE, joinPoint.getSignature().getName(), throwable.getCause() != null ? throwable.getCause() : "NULL");
    }
}
