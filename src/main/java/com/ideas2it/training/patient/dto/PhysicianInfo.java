package com.ideas2it.training.patient.dto;

import lombok.Data;

/**
 * DTO for returning Physician data.
 */
@Data
public class PhysicianInfo {
    private Long id;
    private String name;
    private String contactNumber;
    private String secondaryContactNumber;
    private String email;
    private String specialization;
    private String licenseNumber;
    private String hospital;
    private String officeAddress;
    private int yearsOfExperience;
    private String status;
}

