package com.ideas2it.training.patient.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private LoginResult status;
    private String message;
}
