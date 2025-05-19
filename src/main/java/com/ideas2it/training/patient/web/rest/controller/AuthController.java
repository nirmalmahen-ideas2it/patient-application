package com.ideas2it.training.patient.web.rest.controller;

import com.ideas2it.training.patient.entity.LoginRequest;
import com.ideas2it.training.patient.entity.LoginResponse;
import com.ideas2it.training.patient.entity.LoginResult;
import com.ideas2it.training.patient.entity.TokenResponse;
import com.ideas2it.training.patient.security.TokenContextHolder;
import com.ideas2it.training.patient.webclient.UserValidationClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * REST controller for authentication.
 *
 * <p>This controller provides endpoints for user authentication. It validates
 * user credentials using the {@link UserValidationClient} and generates an access
 * token for authenticated users.</p>
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
@RequiredArgsConstructor
public class AuthController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserValidationClient userValidationClient;

    @Value("${token.expiry.seconds:300}") // Default token expiry time is 5 minutes
    private long tokenTtl;

    /**
     * Authenticates a user.
     *
     * <p>This method validates the provided login request using the user validation
     * service. If the credentials are valid, it generates an access token and stores
     * it in Redis with a time-to-live (TTL). Otherwise, it returns an unauthorized status.</p>
     *
     * @param request    the login request containing user credentials
     * @param authHeader the authorization header containing the bearer token
     * @return a response entity containing the access token or an error message
     */
    @Operation(summary = "Authenticate a user", description = "Validates user credentials and generates an access token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            TokenContextHolder.setToken(token);
            LoginResponse result = userValidationClient.validateUser(request);

            if (result.getStatus() == LoginResult.SUCCESS) {
                redisTemplate.opsForValue().set(token, true, Duration.ofSeconds(tokenTtl));
                return ResponseEntity.ok(new TokenResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } finally {
            TokenContextHolder.clear();
        }
    }
}