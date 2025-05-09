package com.ideas2it.training.patient.webclient;

import com.ideas2it.training.patient.config.FeignClientConfig;
import com.ideas2it.training.patient.entity.LoginRequest;
import com.ideas2it.training.patient.entity.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client for user validation.
 *
 * <p>This client communicates with the user validation service to validate login requests.
 * It uses Feign to simplify HTTP client interactions and is configured with
 *
 * <p>Example usage:</p>
 * <pre>
 * LoginRequest request = new LoginRequest("username", "password");
 * LoginResponse response = userValidationClient.validateUser(request);
 * </pre>
 *
 * @author Alagu Nirmal Mahendran
 * @since 06/05/2025
 */
@FeignClient(name = "sample", configuration = FeignClientConfig.class)
public interface UserValidationClient {

    /**
     * Validates the provided login request.
     *
     * <p>This method sends a POST request to the user validation service with the
     * provided {@link LoginRequest} and returns a {@link LoginResponse} indicating
     * the validation result.</p>
     *
     * @param request the login request containing user credentials
     * @return the login response indicating success or failure
     */
    @PostMapping("/api/auth/validate")
    LoginResponse validateUser(@RequestBody LoginRequest request);
}
