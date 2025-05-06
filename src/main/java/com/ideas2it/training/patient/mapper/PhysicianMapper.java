package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.dto.PhysicianInfo;
import com.ideas2it.training.patient.entity.Physician;
import org.mapstruct.Mapper;

/**
 * Mapper for converting Physician entities to DTOs.
 *
 * <p>This interface is responsible for mapping between the {@link Physician} entity
 * and the {@link PhysicianInfo} DTO. It uses MapStruct for generating the implementation
 * at compile time.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * PhysicianMapper mapper = ...;
 * PhysicianInfo info = mapper.toInfo(physicianEntity);
 * </pre>
 *
 * <p>Note: The implementation of this interface is generated automatically by MapStruct.</p>
 *
 * @author Alagu Nirmal Mahendran
 * @version 1.0
 * @since 06/05/2025
 */
@Mapper(componentModel = "spring")
public interface PhysicianMapper {

    /**
     * Converts a Physician entity to a PhysicianInfo DTO.
     *
     * @param entity the Physician entity to convert
     * @return the corresponding PhysicianInfo DTO
     */
    PhysicianInfo toInfo(Physician entity);
}
