package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.entity.Physician;
import com.ideas2it.training.patient.repository.PhysicianRepository;
import com.ideas2it.training.patient.util.exceptions.PhysicianNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhysicianResolverTest {

    private PhysicianResolver physicianResolver;

    @Mock
    private PhysicianRepository physicianRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        physicianResolver = new PhysicianResolver(physicianRepository);
    }

    @Test
    void testResolvePhysicianWithValidLicenseNumber() {
        // Arrange
        String licenseNumber = "LICENSE123";
        Physician physician = new Physician();
        physician.setLicenseNumber(licenseNumber);
        when(physicianRepository.findByLicenseNumber(licenseNumber)).thenReturn(Optional.of(physician));

        // Act
        Physician result = physicianResolver.resolvePhysician(licenseNumber);

        // Assert
        assertNotNull(result);
        assertEquals(licenseNumber, result.getLicenseNumber());
        verify(physicianRepository, times(1)).findByLicenseNumber(licenseNumber);
    }

    @Test
    void testResolvePhysicianWithNullLicenseNumber() {
        // Act
        Physician result = physicianResolver.resolvePhysician(null);

        // Assert
        assertNull(result);
        verify(physicianRepository, never()).findByLicenseNumber(anyString());
    }

    @Test
    void testResolvePhysicianNotFound() {
        // Arrange
        String licenseNumber = "INVALID_LICENSE";
        when(physicianRepository.findByLicenseNumber(licenseNumber)).thenReturn(Optional.empty());

        // Act & Assert
        PhysicianNotFoundException exception = assertThrows(PhysicianNotFoundException.class, () ->
                physicianResolver.resolvePhysician(licenseNumber));
        assertEquals("Physician not found with license number: " + licenseNumber, exception.getMessage());
        verify(physicianRepository, times(1)).findByLicenseNumber(licenseNumber);
    }
}