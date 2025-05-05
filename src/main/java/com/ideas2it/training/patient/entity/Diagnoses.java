package com.ideas2it.training.patient.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Diagnoses {
    private String primaryDiagnosis;
    private String secondDiagnosis;
    private String thirdDiagnosis;
}

