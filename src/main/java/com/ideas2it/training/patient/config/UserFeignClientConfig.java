package com.ideas2it.training.patient.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
public class UserFeignClientConfig {

    @Value("${spring.security.oauth2.client.registration.user-client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.user-client.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUri;

    private String cachedToken;
    private long tokenExpiryTime;

    @Bean
    public RequestInterceptor keycloakAuthInterceptor() {
        return requestTemplate -> {
            if (cachedToken == null || System.currentTimeMillis() > tokenExpiryTime) {
                cachedToken = fetchAccessToken();
            }
            requestTemplate.header("Authorization", "Bearer " + cachedToken);
        };
    }

    private String fetchAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response = restTemplate.exchange(tokenUri, HttpMethod.POST, request, Map.class);
        Map<String, Object> body = response.getBody();

        cachedToken = (String) body.get("access_token");
        tokenExpiryTime = System.currentTimeMillis() + (((Integer) body.get("expires_in")) - 60) * 1000L;

        return cachedToken;
    }
}

