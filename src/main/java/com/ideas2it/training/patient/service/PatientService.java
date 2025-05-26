package com.ideas2it.training.patient.service;

import com.ideas2it.training.patient.dto.PagedResponse;
import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;

import java.util.List;

/**
 * Service interface for managing patients.
 *
 * <p>This interface defines the contract for patient-related operations, including
 * creating, updating, retrieving, and deleting patient records. It also supports
 * paginated retrieval of patient information.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
public interface PatientService {

    /**
     * Creates a new patient.
     *
     * @param request the patient request containing patient details
     * @return the created patient information
     */
    PatientInfo create(PatientRequest request);

    /**
     * Updates an existing patient.
     *
     * @param id      the ID of the patient to update
     * @param request the patient request containing updated details
     * @return the updated patient information
     */
    PatientInfo update(Long id, PatientRequest request);

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient information
     */
    PatientInfo getById(Long id);

    /**
     * Retrieves all patients.
     *
     * @return a list of all patients
     */
    List<PatientInfo> getAll();

    /**
     * Retrieves a paginated list of patients.
     *
     * @param offset the starting index of the page
     * @param limit  the number of patients per page
     * @return a PagedResponse containing the paginated PatientInfo objects
     */
    PagedResponse<PatientInfo> getAllPaged(int offset, int limit);

    /**
     * Deletes a patient by ID.
     *
     * @param id the ID of the patient to delete
     */
    void delete(Long id);
}