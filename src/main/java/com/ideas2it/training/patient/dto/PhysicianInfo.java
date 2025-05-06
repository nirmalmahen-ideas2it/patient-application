package com.ideas2it.training.patient.dto;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * DTO for returning Physician data.
 */
@Data
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

