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

/**
 * REST controller for authentication.
 *
 * <p>This controller provides an endpoint for user authentication. It validates
 * user credentials using the {@link UserValidationClient} and generates an access
 * token using the {@link KeycloakTokenService}.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * POST /api/authenticate
 * {
 *   "username": "user",
 *   "password": "password"
 * }
 * </pre>
 *
 * @author Alagu Nirmal Mahendran
 * @version 1.0
 * @since 06/05/2025
 */
@RestController
@RequestMapping("/api/authenticate")
public class AuthController {

    private final UserValidationClient userValidationClient;
    private final KeycloakTokenService keycloakTokenService;

    /**
     * Constructs an instance of {@link AuthController}.
     *
     * @param userValidationClient the client for validating user credentials
     * @param keycloakTokenService the service for generating access tokens
     */
    public AuthController(UserValidationClient userValidationClient, KeycloakTokenService keycloakTokenService) {
        this.userValidationClient = userValidationClient;
        this.keycloakTokenService = keycloakTokenService;
    }

    /**
     * Authenticates a user.
     *
     * <p>This method validates the provided login request using the user validation
     * service. If the credentials are valid, it generates an access token and returns
     * it in the response. Otherwise, it returns an unauthorized status.</p>
     *
     * @param request the login request containing user credentials
     * @return a response entity containing the access token or an error message
     */
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
