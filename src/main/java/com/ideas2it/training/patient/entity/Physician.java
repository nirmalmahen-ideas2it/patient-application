package com.ideas2it.training.patient.entity;

import com.ideas2it.training.patient.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "physicians")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Physician extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Physician's full name

    private String contactNumber;  // Primary phone number
    private String secondaryContactNumber;  // Secondary phone number (optional)
    private String email;  // Physician's email address

    private String specialization;  // Field of specialization (e.g. Cardiology, Neurology)
    private String licenseNumber;  // Medical license number

    private String hospital;  // The hospital/clinic where the physician works
    private String officeAddress;  // Office address

    private int yearsOfExperience;  // Number of years of experience

    @Enumerated(EnumType.STRING)
    private Status status;  // Active or inactive status

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}


