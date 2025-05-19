package com.ideas2it.training.patient.dto;

import lombok.Builder;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * DTO for returning Physician data.
 * <p>
 * This class is used to encapsulate physician information that is returned as part of API responses.
 * It includes personal details, contact information, specialization, and professional details.
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Data
@Builder
public class PhysicianInfo implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;
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

