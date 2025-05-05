package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.entity.Patient;
import com.ideas2it.training.patient.entity.Physician;
import com.ideas2it.training.patient.util.exceptions.PhysicianNotFoundException;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PhysicianMapper.class})
public interface PatientMapper {

    @Mapping(source = "primaryPhysician", target = "primaryPhysician")
    PatientInfo toInfo(Patient patient);

    @Mapping(source = "primaryPhysicianLicenseId", target = "primaryPhysician")
    Patient toEntity(PatientRequest request, @Context PhysicianResolver physicianResolver);

    @AfterMapping
    default void linkPhysician(@MappingTarget Patient patient, PatientRequest request, @Context PhysicianResolver physicianResolver) {
        Physician physician = physicianResolver.resolvePhysician(request.getPrimaryPhysicianLicenseId());
        if (physician == null) {
            throw new PhysicianNotFoundException("Physician not found with license number: " + request.getPrimaryPhysicianLicenseId());
        }
        patient.setPrimaryPhysician(physician);
    }

    default Physician map(String licenseNumber, @Context PhysicianResolver resolver) {
        return resolver.resolvePhysician(licenseNumber);
    }
}

