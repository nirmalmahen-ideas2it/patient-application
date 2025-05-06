package com.ideas2it.training.patient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Service for managing Keycloak tokens.
 *
 * <p>This service is responsible for obtaining access tokens from Keycloak using
 * the client credentials grant type. It interacts with the Keycloak token endpoint
 * to retrieve tokens for secure communication with other services.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * KeycloakTokenService tokenService = new KeycloakTokenService();
 * String accessToken = tokenService.getAccessToken();
 * </pre>
 *
 * @author Alagu Nirmal Mahendran
 * @version 1.0
 * @since 06/05/2025
 */
@Service
public class KeycloakTokenService {

    @Value("${spring.security.oauth2.client.registration.patient-client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.patient-client.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUri;

    /**
     * Retrieves an access token from Keycloak.
     *
     * <p>This method uses the client credentials grant type to authenticate with
     * Keycloak and obtain an access token. The token can be used for secure
     * communication with other services.</p>
     *
     * @return the access token as a string
     */
    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        ResponseEntity<Map> response = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, Map.class);
        return (String) response.getBody().get("access_token");
    }
}
