package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.entity.Patient;
import com.ideas2it.training.patient.entity.Physician;
import com.ideas2it.training.patient.util.exceptions.PhysicianNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientMapperTest {

    private PatientMapper patientMapper;

    @Mock
    private PhysicianResolver physicianResolver;

    @Mock
    private PhysicianMapper physicianMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientMapper = Mappers.getMapper(PatientMapper.class);
    }

    @Test
    void testToEntity() {
        PatientRequest request = new PatientRequest();
        request.setFirstName("Jane");
        request.setLastName("Doe");
        request.setPrimaryPhysicianLicenseId("LICENSE456");

        Physician physician = new Physician();
        physician.setLicenseNumber("LICENSE456");
        when(physicianResolver.resolvePhysician("LICENSE456")).thenReturn(physician);

        Patient patient = patientMapper.toEntity(request, physicianResolver);

        assertNotNull(patient);
        assertEquals("Jane Doe", patient.getFirstName() + " " + patient.getLastName());
        assertNotNull(patient.getPrimaryPhysician());
        assertEquals("LICENSE456", patient.getPrimaryPhysician().getLicenseNumber());
    }

    @Test
    void testLinkPhysician() {
        Patient patient = new Patient();
        PatientRequest request = new PatientRequest();
        request.setPrimaryPhysicianLicenseId("LICENSE789");

        Physician physician = new Physician();
        physician.setLicenseNumber("LICENSE789");
        when(physicianResolver.resolvePhysician("LICENSE789")).thenReturn(physician);

        patientMapper.linkPhysician(patient, request, physicianResolver);

        assertNotNull(patient.getPrimaryPhysician());
        assertEquals("LICENSE789", patient.getPrimaryPhysician().getLicenseNumber());
    }

    @Test
    void testLinkPhysicianNotFound() {
        Patient patient = new Patient();
        PatientRequest request = new PatientRequest();
        request.setPrimaryPhysicianLicenseId("INVALID_LICENSE");

        when(physicianResolver.resolvePhysician("INVALID_LICENSE"))
                .thenThrow(new PhysicianNotFoundException("Physician not found with license number: INVALID_LICENSE"));

        PhysicianNotFoundException exception = assertThrows(PhysicianNotFoundException.class, () ->
                patientMapper.linkPhysician(patient, request, physicianResolver));

        assertEquals("Physician not found with license number: INVALID_LICENSE", exception.getMessage());
    }

    @Test
    void testMap() {
        String licenseNumber = "LICENSE123";
        Physician physician = new Physician();
        physician.setLicenseNumber(licenseNumber);
        when(physicianResolver.resolvePhysician(licenseNumber)).thenReturn(physician);

        Physician result = patientMapper.map(licenseNumber, physicianResolver);

        assertNotNull(result);
        assertEquals(licenseNumber, result.getLicenseNumber());
    }

    @Test
    void testMapWithNullLicenseNumber() {
        Physician result = patientMapper.map(null, physicianResolver);

        assertNull(result);
        verify(physicianResolver, never()).resolvePhysician(anyString());
    }
}
