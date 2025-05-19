package com.ideas2it.training.patient.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for managing application-wide exceptions.
 *
 * <p>This class provides centralized exception handling for the application.
 * It uses Spring's {@link ControllerAdvice} to intercept exceptions and
 * return appropriate HTTP responses.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles PhysicianNotFoundException.
     *
     * <p>This method intercepts {@link PhysicianNotFoundException} and returns
     * a 400 Bad Request response with the exception message.</p>
     *
     * @param ex the PhysicianNotFoundException instance
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(PhysicianNotFoundException.class)
    public ResponseEntity<String> handlePhysicianNotFoundException(PhysicianNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}