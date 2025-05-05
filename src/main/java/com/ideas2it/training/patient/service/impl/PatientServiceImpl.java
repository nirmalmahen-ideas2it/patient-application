package com.ideas2it.training.patient.service.impl;

import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.entity.Patient;
import com.ideas2it.training.patient.mapper.PatientMapper;
import com.ideas2it.training.patient.mapper.PhysicianResolver;
import com.ideas2it.training.patient.repository.PatientRepository;
import com.ideas2it.training.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;
    private final PhysicianResolver physicianResolver;

    @Override
    public PatientInfo create(PatientRequest request) {
        Patient patient = mapper.toEntity(request, physicianResolver);
        return mapper.toInfo(repository.save(patient));
    }

    @Override
    public PatientInfo update(Long id, PatientRequest request) {
        Patient existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
        Patient updated = mapper.toEntity(request, physicianResolver);
        updated.setId(existing.getId());
        return mapper.toInfo(repository.save(updated));
    }

    @Override
    public PatientInfo getById(Long id) {
        return mapper.toInfo(repository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found")));
    }

    @Override
    public List<PatientInfo> getAll() {
        return repository.findAll().stream().map(mapper::toInfo).collect(Collectors.toList());
    }

    @Override
    public Page<PatientInfo> getAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toInfo);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
