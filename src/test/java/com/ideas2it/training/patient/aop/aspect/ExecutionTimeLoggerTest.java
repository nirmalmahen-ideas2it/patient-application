package com.ideas2it.training.patient.aop.aspect;

import com.ideas2it.training.patient.aop.annotations.LogExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExecutionTimeLoggerTest {

    private ExecutionTimeLogger executionTimeLogger;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private LogExecutionTime logExecutionTime;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        executionTimeLogger = new ExecutionTimeLogger();
    }

    @Test
    void testLogExecutionTimeSuccess() throws Throwable {
        when(joinPoint.proceed()).thenReturn("Test Result");
        Signature signature = mock(Signature.class);
        when(signature.toString()).thenReturn("testMethod");
        when(joinPoint.getSignature()).thenReturn(signature);

        Object result = executionTimeLogger.logExecutionTime(joinPoint, logExecutionTime);

        assertEquals("Test Result", result);
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    void testLogExecutionTimeWithException() throws Throwable {
        when(joinPoint.proceed()).thenThrow(new RuntimeException("Test Exception"));
        Signature signature = mock(Signature.class);
        when(signature.toString()).thenReturn("testMethod");
        when(joinPoint.getSignature()).thenReturn(signature);

        try {
            executionTimeLogger.logExecutionTime(joinPoint, logExecutionTime);
        } catch (RuntimeException e) {
            assertEquals("Test Exception", e.getMessage());
        }

        verify(joinPoint, times(1)).proceed();
    }

    @Test
    void testLogExecutionTimeWithNullReturn() throws Throwable {
        when(joinPoint.proceed()).thenReturn(null);
        Signature signature = mock(Signature.class);
        when(signature.toString()).thenReturn("testMethod");
        when(joinPoint.getSignature()).thenReturn(signature);

        Object result = executionTimeLogger.logExecutionTime(joinPoint, logExecutionTime);

        assertEquals(null, result);
        verify(joinPoint, times(1)).proceed();
    }
}
