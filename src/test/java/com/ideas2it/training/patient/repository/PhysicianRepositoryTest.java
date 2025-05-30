package com.ideas2it.training.patient.repository;

import com.ideas2it.training.patient.entity.Physician;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ImportAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class})
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.vault.enabled=false",
        "spring.cloud.consul.enabled=false"
})
class PhysicianRepositoryTest {

    @Autowired
    private PhysicianRepository physicianRepository;

    private Physician physician;

    @BeforeEach
    void setUp() {
        physician = new Physician();
        physician.setName("Dr. John Doe");
        physician.setLicenseNumber("LICENSE123");
        physician.setSpecialization("Cardiology");
        physician.setEmail("johndoe@example.com");
        physician.setCreatedBy("SYSTEM");
    }

    @Test
    void testFindByLicenseNumberWithValidLicense() {
        // Arrange
        physicianRepository.save(physician);

        // Act
        Optional<Physician> foundPhysician = physicianRepository.findByLicenseNumber("LICENSE123");

        // Assert
        assertTrue(foundPhysician.isPresent());
        assertEquals("LICENSE123", foundPhysician.get().getLicenseNumber());
        assertEquals("Dr. John Doe", foundPhysician.get().getName());
    }

    @Test
    void testFindByLicenseNumberWithNonExistentLicense() {
        // Act
        Optional<Physician> foundPhysician = physicianRepository.findByLicenseNumber("INVALID_LICENSE");

        // Assert
        assertFalse(foundPhysician.isPresent());
    }

    @Test
    void testFindByLicenseNumberWithNullLicense() {
        // Act
        Optional<Physician> foundPhysician = physicianRepository.findByLicenseNumber(null);

        // Assert
        assertFalse(foundPhysician.isPresent());
    }

    @Test
    void testFindByLicenseNumberWithEmptyLicense() {
        // Act
        Optional<Physician> foundPhysician = physicianRepository.findByLicenseNumber("");

        // Assert
        assertFalse(foundPhysician.isPresent());
    }
}