package com.ideas2it.training.patient.service;

import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    PatientInfo create(PatientRequest request);

    PatientInfo update(Long id, PatientRequest request);

    PatientInfo getById(Long id);

    List<PatientInfo> getAll();

    Page<PatientInfo> getAllPaged(Pageable pageable);

    void delete(Long id);
}
