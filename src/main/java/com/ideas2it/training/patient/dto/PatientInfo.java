package com.ideas2it.training.patient.dto;

import com.ideas2it.training.patient.entity.Diagnoses;
import com.ideas2it.training.patient.entity.ReferralInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for returning Patient data.
 * <p>
 * This class is used to encapsulate patient information that is returned as part of API responses.
 * It includes personal details, medical information, and associated physician details.
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientInfo implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;
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

