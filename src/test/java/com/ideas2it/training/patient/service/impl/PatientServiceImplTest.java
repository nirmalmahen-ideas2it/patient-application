package com.ideas2it.training.patient.service.impl;

import com.ideas2it.training.patient.dto.PagedResponse;
import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.entity.Patient;
import com.ideas2it.training.patient.mapper.PatientMapper;
import com.ideas2it.training.patient.mapper.PhysicianResolver;
import com.ideas2it.training.patient.publish.PatientInfoPublisher;
import com.ideas2it.training.patient.repository.PatientRepository;
import com.ideas2it.training.patient.service.PatientMetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository repository;

    @Mock
    private PatientMetricService metricService;

    @Mock
    private PatientMapper mapper;

    @Mock
    private PhysicianResolver physicianResolver;

    @Mock
    private PatientInfoPublisher patientInfoPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientServiceImpl(repository, metricService, mapper, physicianResolver, patientInfoPublisher);
    }

    @Test
    void testCreate() {
        // Arrange
        PatientRequest request = new PatientRequest();
        Patient patient = new Patient();
        Patient savedPatient = new Patient();
        PatientInfo patientInfo = new PatientInfo();

        when(mapper.toEntity(request, physicianResolver)).thenReturn(patient);
        when(repository.save(patient)).thenReturn(savedPatient);
        when(mapper.toInfo(savedPatient)).thenReturn(patientInfo);

        // Act
        PatientInfo result = patientService.create(request);

        // Assert
        assertEquals(patientInfo, result);
        verify(metricService, times(1)).incrementPatientCount();
        verify(patientInfoPublisher, times(1)).sendPatientInfo(patientInfo);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        PatientRequest request = new PatientRequest();
        Patient existingPatient = new Patient();
        Patient updatedPatient = new Patient();
        Patient savedPatient = new Patient();
        PatientInfo patientInfo = new PatientInfo();

        when(repository.findById(id)).thenReturn(Optional.of(existingPatient));
        when(mapper.toEntity(request, physicianResolver)).thenReturn(updatedPatient);
        when(repository.save(updatedPatient)).thenReturn(savedPatient);
        when(mapper.toInfo(savedPatient)).thenReturn(patientInfo);

        // Act
        PatientInfo result = patientService.update(id, request);

        // Assert
        assertEquals(patientInfo, result);
    }

    @Test
    void testUpdatePatientNotFound() {
        // Arrange
        Long id = 1L;
        PatientRequest request = new PatientRequest();

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> patientService.update(id, request));
    }

    @Test
    void testGetById() {
        // Arrange
        Long id = 1L;
        Patient patient = new Patient();
        PatientInfo patientInfo = new PatientInfo();

        when(repository.findById(id)).thenReturn(Optional.of(patient));
        when(mapper.toInfo(patient)).thenReturn(patientInfo);

        // Act
        PatientInfo result = patientService.getById(id);

        // Assert
        assertEquals(patientInfo, result);
    }

    @Test
    void testGetByIdPatientNotFound() {
        // Arrange
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> patientService.getById(id));
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Patient> patients = List.of(new Patient());
        List<PatientInfo> patientInfos = List.of(new PatientInfo());

        when(repository.findAll()).thenReturn(patients);
        when(mapper.toInfo(any(Patient.class))).thenReturn(patientInfos.get(0));

        // Act
        List<PatientInfo> result = patientService.getAll();

        // Assert
        assertEquals(patientInfos, result);
    }

    @Test
    void testGetAllPaged() {
        // Arrange
        int offset = 0;
        int limit = 10;
        List<Patient> patients = List.of(new Patient());
        List<PatientInfo> patientInfos = List.of(new PatientInfo());
        Page<Patient> page = new PageImpl<>(patients);

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);
        when(mapper.toInfo(any(Patient.class))).thenReturn(patientInfos.get(0));

        // Act
        PagedResponse<PatientInfo> result = patientService.getAllPaged(offset, limit);

        // Assert
        assertEquals(patientInfos, result.getItems());
        assertEquals(page.getTotalElements(), result.getTotalElements());
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        patientService.delete(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}