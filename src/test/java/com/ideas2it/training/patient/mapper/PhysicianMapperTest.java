package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.dto.PhysicianInfo;
import com.ideas2it.training.patient.entity.Physician;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PhysicianMapperTest {

    private final PhysicianMapper physicianMapper = Mappers.getMapper(PhysicianMapper.class);

    @Test
    void testToInfoWithValidEntity() {
        // Arrange
        Physician physician = new Physician();
        physician.setId(1L);
        physician.setName("Dr. John Doe");
        physician.setContactNumber("1234567890");
        physician.setSecondaryContactNumber("0987654321");
        physician.setEmail("johndoe@example.com");
        physician.setSpecialization("Cardiology");
        physician.setLicenseNumber("LICENSE123");
        physician.setHospital("City Hospital");
        physician.setOfficeAddress("123 Main St");
        physician.setYearsOfExperience(15);
        physician.setStatus(Physician.Status.ACTIVE);

        // Act
        PhysicianInfo physicianInfo = physicianMapper.toInfo(physician);

        // Assert
        assertNotNull(physicianInfo);
        assertEquals(1L, physicianInfo.getId());
        assertEquals("Dr. John Doe", physicianInfo.getName());
        assertEquals("1234567890", physicianInfo.getContactNumber());
        assertEquals("0987654321", physicianInfo.getSecondaryContactNumber());
        assertEquals("johndoe@example.com", physicianInfo.getEmail());
        assertEquals("Cardiology", physicianInfo.getSpecialization());
        assertEquals("LICENSE123", physicianInfo.getLicenseNumber());
        assertEquals("City Hospital", physicianInfo.getHospital());
        assertEquals("123 Main St", physicianInfo.getOfficeAddress());
        assertEquals(15, physicianInfo.getYearsOfExperience());
        assertEquals("ACTIVE", physicianInfo.getStatus());
    }

    @Test
    void testToInfoWithNullEntity() {
        // Act
        PhysicianInfo physicianInfo = physicianMapper.toInfo(null);

        // Assert
        assertNull(physicianInfo);
    }

    @Test
    void testToInfoWithIncompleteEntity() {
        // Arrange
        Physician physician = new Physician();
        physician.setId(2L);
        physician.setName("Dr. Jane Smith");
        physician.setContactNumber(null);
        physician.setEmail("janesmith@example.com");
        physician.setSpecialization(null);
        physician.setLicenseNumber("LICENSE456");
        physician.setYearsOfExperience(0);
        physician.setStatus(null);

        // Act
        PhysicianInfo physicianInfo = physicianMapper.toInfo(physician);

        // Assert
        assertNotNull(physicianInfo);
        assertEquals(2L, physicianInfo.getId());
        assertEquals("Dr. Jane Smith", physicianInfo.getName());
        assertNull(physicianInfo.getContactNumber());
        assertEquals("janesmith@example.com", physicianInfo.getEmail());
        assertNull(physicianInfo.getSpecialization());
        assertEquals("LICENSE456", physicianInfo.getLicenseNumber());
        assertEquals(0, physicianInfo.getYearsOfExperience());
        assertNull(physicianInfo.getStatus());
    }
}