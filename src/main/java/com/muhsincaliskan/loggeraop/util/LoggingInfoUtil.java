package com.muhsincaliskan.loggeraop.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.HashMap;
import java.util.Map;

public class LoggingInfoUtil {

    private static final String BEFORE_LOG_METHOD = "%s started with parameters : %s";
    private static final String AFTER_LOG_EXECUTE_DURATION = "%s executed in %d ms";
    private static final String AFTER_LOG_RETURNED_VALUE = "%s finished with return value: %s";

    public static String getBeforeLogMethod(ProceedingJoinPoint proceedingJoinPoint, String methodName) {

        Map<String, Object> parameters = getParameters(proceedingJoinPoint);
        return String.format(BEFORE_LOG_METHOD, methodName, parameters);

    }

    public static String getAfterLogReturnedValue(ProceedingJoinPoint proceedingJoinPoint, String methodName) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed();
        return String.format(AFTER_LOG_RETURNED_VALUE, methodName, proceed);
    }

    public static String getAfterLogExecuteDuration(long startTimeMillis, String methodName) {
        final long executionTime = System.currentTimeMillis() - startTimeMillis;
        return String.format(AFTER_LOG_EXECUTE_DURATION, methodName, executionTime);
    }

    public static Map<String, Object> getParameters(ProceedingJoinPoint proceedingJoinPoint) {
        Map<String, Object> parameters = new HashMap<>();
        String[] parameterNames = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = proceedingJoinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            parameters.put(parameterNames[i], parameterValues[i]);
        }
        return parameters;
    }
}
