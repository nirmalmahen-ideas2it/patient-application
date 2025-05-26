package com.ideas2it.training.patient.aop.aspect;

import com.ideas2it.training.patient.aop.annotations.LogExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeLogger {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeLogger.class);

    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();  // Method execution

        long end = System.currentTimeMillis();
        logger.info("Method [{}] executed in {} ms", joinPoint.getSignature(), (end - start));

        return result;
    }
}
