package com.ideas2it.training.patient.repository;

import com.ideas2it.training.patient.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
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
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setAddress("123 Main St");
        patient.setMedicalRecordNumber("MRN123");
        patient.setCreatedBy("SYSTEM");
    }

    @Test
    void testSaveValidPatient() {
        // Act
        patient.setMedicalRecordNumber("MRN12345");
        Patient savedPatient = patientRepository.save(patient);

        // Assert
        assertNotNull(savedPatient.getId());
        assertEquals("John Doe", savedPatient.getFirstName() + " " + savedPatient.getLastName());
        assertEquals("123 Main St", savedPatient.getAddress());
    }

    @Test
    void testSaveNullPatient() {
        // Act & Assert
        assertThrows(Exception.class, () -> patientRepository.save(null));
    }

    @Test
    void testFindByIdExistingPatient() {
        // Arrange
        patient.setMedicalRecordNumber("MRN12");
        Patient savedPatient = patientRepository.save(patient);

        // Act
        Optional<Patient> foundPatient = patientRepository.findById(savedPatient.getId());

        // Assert
        assertTrue(foundPatient.isPresent());
        assertEquals(savedPatient.getId(), foundPatient.get().getId());
    }

    @Test
    void testFindByIdNonExistentPatient() {
        // Act
        Optional<Patient> foundPatient = patientRepository.findById(999L);

        // Assert
        assertFalse(foundPatient.isPresent());
    }

    @Test
    void testFindAllWithPatients() {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setFirstName("Jane");
        patient1.setLastName("Smith");
        patient1.setAddress("456 Elm St");
        patient1.setMedicalRecordNumber("MRN1234");
        patient1.setCreatedBy("SYSTEM");
        patientRepository.save(patient);
        patientRepository.save(patient1);

        // Act
        List<Patient> patients = patientRepository.findAll();

        // Assert
        assertEquals(2, patients.size());
    }

    @Test
    void testDeleteByIdExistingPatient() {
        // Arrange
        patient.setMedicalRecordNumber("MRN1");
        Patient savedPatient = patientRepository.save(patient);

        // Act
        patientRepository.deleteById(savedPatient.getId());

        // Assert
        Optional<Patient> deletedPatient = patientRepository.findById(savedPatient.getId());
        assertFalse(deletedPatient.isPresent());
    }

    @Test
    void testDeleteByIdNonExistentPatient() {
        // Act & Assert
        assertDoesNotThrow(() -> patientRepository.deleteById(999L));
    }

    @Test
    void testDeleteByIdWithNull() {
        // Act & Assert
        assertThrows(Exception.class, () -> patientRepository.deleteById(null));
    }
}
