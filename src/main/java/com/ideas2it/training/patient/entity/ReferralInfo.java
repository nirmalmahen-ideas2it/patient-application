package com.ideas2it.training.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ReferralInfo {
    private String referrerName;
    @Column(name = "referrer_email")
    private String email;
    @Column(name = "referrer_mobile")
    private String mobile;
}

