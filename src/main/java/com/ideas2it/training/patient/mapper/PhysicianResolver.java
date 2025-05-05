package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.entity.Physician;
import com.ideas2it.training.patient.repository.PhysicianRepository;
import com.ideas2it.training.patient.util.exceptions.PhysicianNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class PhysicianResolver {
    private final PhysicianRepository repository;

    public PhysicianResolver(PhysicianRepository repository) {
        this.repository = repository;
    }

    @Named("resolvePhysician")
    public Physician resolvePhysician(String licenseNumber) {
        return licenseNumber != null
            ? repository.findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new PhysicianNotFoundException("Physician not found with license number: " + licenseNumber))
            : null;
    }
}
