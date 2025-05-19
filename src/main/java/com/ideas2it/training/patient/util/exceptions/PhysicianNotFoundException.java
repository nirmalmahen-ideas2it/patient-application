package com.ideas2it.training.patient.util.exceptions;

/**
 * Custom exception for handling cases where a physician is not found.
 *
 * <p>This exception is thrown when a requested physician cannot be located
 * in the system. It extends {@link RuntimeException} to allow unchecked
 * exception handling.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
public class PhysicianNotFoundException extends RuntimeException {

    /**
     * Constructor for creating a new PhysicianNotFoundException.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public PhysicianNotFoundException(String message) {
        super(message);
    }
}