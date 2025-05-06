package com.ideas2it.training.patient.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;

@Data
@Embeddable
public class Diagnoses implements Serializable {
    @JsonIgnore
    @Transient
    private static final long serialVersionUID = 1L;
    private String primaryDiagnosis;
    private String secondDiagnosis;
    private String thirdDiagnosis;
}

