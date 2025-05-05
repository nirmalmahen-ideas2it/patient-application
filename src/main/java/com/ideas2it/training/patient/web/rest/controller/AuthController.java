package com.ideas2it.training.patient.web.rest.controller;

import com.ideas2it.training.patient.entity.LoginRequest;
import com.ideas2it.training.patient.entity.LoginResponse;
import com.ideas2it.training.patient.entity.LoginResult;
import com.ideas2it.training.patient.entity.TokenResponse;
import com.ideas2it.training.patient.service.KeycloakTokenService;
import com.ideas2it.training.patient.webclient.UserValidationClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
public class AuthController {

    private final UserValidationClient userValidationClient;
    private final KeycloakTokenService keycloakTokenService;

    public AuthController(UserValidationClient userValidationClient, KeycloakTokenService keycloakTokenService) {
        this.userValidationClient = userValidationClient;
        this.keycloakTokenService = keycloakTokenService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request) {
        LoginResponse result = userValidationClient.validateUser(request);

        if (result.getStatus() == LoginResult.SUCCESS) {
            String token = keycloakTokenService.getAccessToken();
            return ResponseEntity.ok(new TokenResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
