package com.ideas2it.training.patient.service.impl;

import com.ideas2it.training.patient.dto.PagedResponse;
import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.entity.Patient;
import com.ideas2it.training.patient.mapper.PatientMapper;
import com.ideas2it.training.patient.mapper.PhysicianResolver;
import com.ideas2it.training.patient.repository.PatientRepository;
import com.ideas2it.training.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing patients.
 *
 * <p>This service provides methods for creating, updating, retrieving, and deleting patient records.
 * It interacts with the {@link PatientRepository} for database operations and uses the
 * {@link PatientMapper} for mapping between DTOs and entities.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * PatientRequest request = new PatientRequest("John Doe", 30, "123 Main St");
 * PatientInfo info = patientService.create(request);
 * </pre>
 *
 * @author Alagu Nirmal Mahendran
 * @version 1.0
 * @since 06/05/2025
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;
    private final PhysicianResolver physicianResolver;

    /**
     * Creates a new patient.
     *
     * @param request the patient request containing patient details
     * @return the created patient information
     */
    @Override
    public PatientInfo create(PatientRequest request) {
        Patient patient = mapper.toEntity(request, physicianResolver);
        return mapper.toInfo(repository.save(patient));
    }

    /**
     * Updates an existing patient.
     *
     * @param id      the ID of the patient to update
     * @param request the patient request containing updated details
     * @return the updated patient information
     */
    @Override
    @CachePut(value = "patients", key = "#id")
    public PatientInfo update(Long id, PatientRequest request) {
        Patient existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
        Patient updated = mapper.toEntity(request, physicianResolver);
        updated.setId(existing.getId());
        return mapper.toInfo(repository.save(updated));
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient information
     */
    @Cacheable(value = "patients", key = "#id")
    @Override
    public PatientInfo getById(Long id) {
        return mapper.toInfo(repository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found")));
    }

    /**
     * Retrieves all patients.
     *
     * @return a list of all patients
     */
    @Override
    public List<PatientInfo> getAll() {
        return repository.findAll().stream().map(mapper::toInfo).collect(Collectors.toList());
    }

    /**
     * Retrieves a paginated list of roles.
     *
     * @param offset the starting index of the page
     * @param limit  the number of roles per page
     * @return a PagedResponse containing the paginated RoleInfo objects
     */
    public PagedResponse<PatientInfo> getAllPaged(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        Page<Patient> page = repository.findAll(pageRequest);

        List<PatientInfo> patientInfos = page.getContent().stream()
            .map(mapper::toInfo)
            .toList();

        return new PagedResponse<>(
            patientInfos,
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id the ID of the patient to delete
     */
    @Override
    @CacheEvict(value = "patients", key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
