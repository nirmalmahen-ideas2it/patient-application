package com.ideas2it.training.patient.entity;

import com.ideas2it.training.patient.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Embedded
    private ReferralInfo referralInfo;

    @Embedded
    private Diagnoses diagnoses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "physician_id")
    private Physician primaryPhysician;
}
