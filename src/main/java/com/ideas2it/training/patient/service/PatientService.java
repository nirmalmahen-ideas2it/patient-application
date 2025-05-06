package com.ideas2it.training.patient.service;

import com.ideas2it.training.patient.dto.PagedResponse;
import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;

import java.util.List;

public interface PatientService {
    PatientInfo create(PatientRequest request);

    PatientInfo update(Long id, PatientRequest request);

    PatientInfo getById(Long id);

    List<PatientInfo> getAll();

    PagedResponse<PatientInfo> getAllPaged(int offset, int limit);

    void delete(Long id);
}
