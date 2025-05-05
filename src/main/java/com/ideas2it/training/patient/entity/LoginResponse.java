package com.ideas2it.training.patient.entity;

import lombok.Data;

@Data
public class LoginResponse {
    private LoginResult status;
    private String message;
}
