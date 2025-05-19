package com.ideas2it.training.patient.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PatientMetricServiceTest {

    private PatientMetricService patientMetricService;

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private Counter patientCounter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(meterRegistry.counter("patient.registered.count")).thenReturn(patientCounter);
        patientMetricService = new PatientMetricService(meterRegistry);
    }

    @Test
    void testIncrementPatientCount() {
        // Act
        patientMetricService.incrementPatientCount();

        // Assert
        verify(patientCounter, times(1)).increment();
    }

    @Test
    void testIncrementPatientCountMultipleTimes() {
        // Act
        patientMetricService.incrementPatientCount();
        patientMetricService.incrementPatientCount();
        patientMetricService.incrementPatientCount();

        // Assert
        verify(patientCounter, times(3)).increment();
    }
}