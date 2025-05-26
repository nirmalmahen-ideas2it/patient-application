package com.ideas2it.training.patient.web.rest.controller;

import com.ideas2it.training.patient.dto.PagedResponse;
import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing patients.
 *
 * <p>This controller provides endpoints for creating, updating, retrieving, and deleting patient records.
 * It interacts with the {@link PatientService} to perform the necessary operations.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * POST /api/patients
 * {
 *   "name": "John Doe",
 *   "age": 30,
 *   "address": "123 Main St"
 * }
 * </pre>
 *
 * @author Alagu Nirmal Mahendran
 * @version 1.0
 * @since 06/05/2025
 */
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    /**
     * Creates a new patient.
     *
     * @param request the patient request containing patient details
     * @return the created patient information
     */
    @Operation(summary = "Create a new patient", description = "Creates a new patient record in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid patient request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PatientInfo> create(@RequestBody PatientRequest request) {
        PatientInfo createdPatient = service.create(request);
        return ResponseEntity.ok(createdPatient);
    }

    /**
     * Updates an existing patient.
     *
     * @param id      the ID of the patient to update
     * @param request the patient request containing updated details
     * @return the updated patient information
     */
    @Operation(summary = "Update an existing patient", description = "Updates the details of an existing patient.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientInfo> update(@PathVariable Long id, @RequestBody PatientRequest request) {
        PatientInfo updatedPatient = service.update(id, request);
        return ResponseEntity.ok(updatedPatient);
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient information
     */
    @Operation(summary = "Get a patient by ID", description = "Retrieves the details of a patient by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientInfo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Retrieves all patients.
     *
     * @return a list of all patients
     */
    @Operation(summary = "Get all patients", description = "Retrieves a list of all patients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PatientInfo>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Retrieves all patients in a paginated format.
     *
     * @param offset,limit contains the pagination information
     * @return a paginated list of patients
     */
    @Operation(summary = "Get all patients (paginated)", description = "Retrieves a paginated list of all patients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<PatientInfo>> getAllPaged(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(service.getAllPaged(offset, limit));
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id the ID of the patient to delete
     */
    @Operation(summary = "Delete a patient by ID", description = "Deletes a patient record by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
