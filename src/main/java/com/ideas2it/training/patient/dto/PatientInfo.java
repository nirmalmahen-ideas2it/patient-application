package com.ideas2it.training.patient.dto;

import com.ideas2it.training.patient.entity.Diagnoses;
import com.ideas2it.training.patient.entity.ReferralInfo;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for returning Patient data.
 */
@Data
public class PatientInfo {
    private Long id;
    private String medicalRecordNumber;
    private LocalDate startOfCareDate;
    private String status;
    private String firstName;
    private String lastName;
    private String sex;
    private LocalDate birthDate;
    private String maritalStatus;
    private String address;
    private String city;
    private String state;
    private String county;
    private String zipCode;
    private String email;
    private String mobile;
    private ReferralInfo referralInfo;
    private Diagnoses diagnoses;
    private PhysicianInfo primaryPhysician;
}

