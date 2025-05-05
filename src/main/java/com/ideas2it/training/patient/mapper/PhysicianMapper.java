package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.dto.PhysicianInfo;
import com.ideas2it.training.patient.entity.Physician;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhysicianMapper {
    PhysicianInfo toInfo(Physician entity);
}

