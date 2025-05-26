package com.ideas2it.training.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideas2it.training.patient.dto.PagedResponse;
import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.service.PatientService;
import com.ideas2it.training.patient.web.rest.controller.PatientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class,
        excludeAutoConfiguration = {OAuth2ResourceServerAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)  // disables security filters for test
@WithMockUser(roles = "USER")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate;

    private PatientRequest patientRequest;
    private PatientInfo patientInfo;

    @BeforeEach
    public void setup() throws Exception {
        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.hasClaim(any(String.class))).thenReturn(false);
        Mockito.when(jwtDecoder.decode(any(String.class))).thenReturn(jwt);

        patientRequest = new PatientRequest();
        patientRequest.setFirstName("John");
        patientRequest.setLastName("Doe");
        patientRequest.setAddress("123 Main St");
        patientRequest.setMedicalRecordNumber("MRN123");

        patientInfo = PatientInfo.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .medicalRecordNumber("MRN123")
                .build();
    }

    @Test
    void testCreate() throws Exception {
        Mockito.when(patientService.create(patientRequest)).thenReturn(patientInfo);

        mockMvc.perform(post("/api/patients")
                        .header("Authorization", "Bearer mock-jwt-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(patientInfo)));
    }

    @Test
    void testUpdate() throws Exception {
        Long id = 1L;
        PatientRequest request = new PatientRequest();
        Mockito.when(patientService.update(eq(id), any(PatientRequest.class))).thenReturn(patientInfo);

        mockMvc.perform(put("/api/patients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(patientInfo)));
    }

    @Test
    void testGetById() throws Exception {
        Long id = 1L;
        Mockito.when(patientService.getById(id)).thenReturn(patientInfo);

        mockMvc.perform(get("/api/patients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(patientInfo)));
    }

    @Test
    void testGetAll() throws Exception {
        List<PatientInfo> response = Collections.singletonList(patientInfo);
        Mockito.when(patientService.getAll()).thenReturn(response);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testGetAllPaged() throws Exception {
        int offset = 0;
        int limit = 10;
        PagedResponse<PatientInfo> response = new PagedResponse<>();
        response.setSize(1);
        response.setPage(0);
        response.setItems(Collections.singletonList(patientInfo));

        Mockito.when(patientService.getAllPaged(offset, limit)).thenReturn(response);

        mockMvc.perform(get("/api/patients/paged")
                        .param("offset", String.valueOf(offset))
                        .param("limit", String.valueOf(limit))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testDelete() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(patientService).delete(id);

        mockMvc.perform(delete("/api/patients/{id}", id))
                .andExpect(status().isOk());
    }
}
