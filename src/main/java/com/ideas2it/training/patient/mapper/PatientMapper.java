package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.entity.Patient;
import com.ideas2it.training.patient.entity.Physician;
import com.ideas2it.training.patient.util.exceptions.PhysicianNotFoundException;
import org.mapstruct.*;

/**
 * Mapper for converting Patient entities to DTOs and vice versa.
 *
 * <p>This interface is responsible for mapping between the {@link Patient} entity,
 * the {@link PatientRequest} DTO, and the {@link PatientInfo} DTO. It uses MapStruct
 * for generating the implementation at compile time and integrates with the
 * {@link PhysicianMapper} and {@link PhysicianResolver} for handling physician-related mappings.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * PatientMapper mapper = ...;
 * PatientInfo info = mapper.toInfo(patientEntity);
 * Patient entity = mapper.toEntity(patientRequest, physicianResolver);
 * </pre>
 *
 * <p>Note: The implementation of this interface is generated automatically by MapStruct.</p>
 *
 * @author
 * @version 1.0
 * @since 06/05/2025
 */
@Mapper(componentModel = "spring", uses = {PhysicianMapper.class})
public interface PatientMapper {

    /**
     * Converts a Patient entity to a PatientInfo DTO.
     *
     * @param patient the Patient entity to convert
     * @return the corresponding PatientInfo DTO
     */
    @Mapping(source = "primaryPhysician", target = "primaryPhysician")
    PatientInfo toInfo(Patient patient);

    /**
     * Converts a PatientRequest DTO to a Patient entity.
     *
     * <p>This method maps the primary physician license ID from the request to the
     * corresponding Physician entity using the {@link PhysicianResolver}.</p>
     *
     * @param request           the PatientRequest DTO to convert
     * @param physicianResolver the resolver for mapping physician license IDs
     * @return the corresponding Patient entity
     */
    @Mapping(source = "primaryPhysicianLicenseId", target = "primaryPhysician")
    Patient toEntity(PatientRequest request, @Context PhysicianResolver physicianResolver);

    /**
     * Links the resolved Physician entity to the Patient entity after mapping.
     *
     * <p>This method is executed after the main mapping process to ensure that the
     * primary physician is correctly resolved and linked to the Patient entity.</p>
     *
     * @param patient           the Patient entity being mapped
     * @param request           the PatientRequest DTO being mapped
     * @param physicianResolver the resolver for mapping physician license IDs
     */
    @AfterMapping
    default void linkPhysician(@MappingTarget Patient patient, PatientRequest request, @Context PhysicianResolver physicianResolver) {
        Physician physician = physicianResolver.resolvePhysician(request.getPrimaryPhysicianLicenseId());
        if (physician == null) {
            throw new PhysicianNotFoundException("Physician not found with license number: " + request.getPrimaryPhysicianLicenseId());
        }
        patient.setPrimaryPhysician(physician);
    }

    /**
     * Maps a physician license number to a Physician entity using the resolver.
     *
     * @param licenseNumber the license number of the physician
     * @param resolver      the resolver for mapping physician license IDs
     * @return the corresponding Physician entity
     */
    default Physician map(String licenseNumber, @Context PhysicianResolver resolver) {
        return resolver.resolvePhysician(licenseNumber);
    }
}
