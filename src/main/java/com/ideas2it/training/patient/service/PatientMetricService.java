package com.ideas2it.training.patient.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

/**
 * Service for managing patient-related metrics.
 *
 * <p>This service provides methods to track and increment metrics related to
 * patient registrations. It uses Micrometer's {@link Counter} to record the
 * number of patients registered in the system.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Service
public class PatientMetricService {

    /**
     * Counter for tracking the number of registered patients.
     */
    private final Counter patientCounter;

    /**
     * Constructor for injecting the MeterRegistry dependency.
     *
     * @param meterRegistry the MeterRegistry used to create and manage metrics
     */
    public PatientMetricService(MeterRegistry meterRegistry) {
        this.patientCounter = meterRegistry.counter("patient.registered.count");
    }

    /**
     * Increments the patient registration counter.
     *
     * <p>This method is called whenever a new patient is registered to update
     * the metric tracking the total number of registered patients.</p>
     */
    public void incrementPatientCount() {
        patientCounter.increment();
    }
}