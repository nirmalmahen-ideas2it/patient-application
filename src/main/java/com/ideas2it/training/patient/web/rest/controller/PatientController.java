package com.ideas2it.training.patient.web.rest.controller;

import com.ideas2it.training.patient.dto.PatientInfo;
import com.ideas2it.training.patient.dto.PatientRequest;
import com.ideas2it.training.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @Operation(summary = "Create a new patient")
    @ApiResponse(responseCode = "200", description = "Patient created")
    @PostMapping
    public PatientInfo create(@RequestBody PatientRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Update an existing patient")
    @PutMapping("/{id}")
    public PatientInfo update(@PathVariable Long id, @RequestBody PatientRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Get a patient by ID")
    @GetMapping("/{id}")
    public PatientInfo getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all patients")
    @GetMapping
    public List<PatientInfo> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get all patients (paginated)")
    @GetMapping("/paged")
    public Page<PatientInfo> getAllPaged(Pageable pageable) {
        return service.getAllPaged(pageable);
    }

    @Operation(summary = "Delete a patient by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
