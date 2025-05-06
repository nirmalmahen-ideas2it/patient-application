package com.ideas2it.training.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;

@Data
@Embeddable
public class ReferralInfo implements Serializable {
    @JsonIgnore
    @Transient
    private static final long serialVersionUID = 1L;
    private String referrerName;
    @Column(name = "referrer_email")
    private String email;
    @Column(name = "referrer_mobile")
    private String mobile;
}

