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

/**
 * Configuration for Feign client with Keycloak authentication.
 *
 * <p>This configuration class provides a Feign `RequestInterceptor` that automatically
 * adds a Bearer token to outgoing requests. The token is fetched from Keycloak using
 * the client credentials grant type and is cached until it expires.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * FeignClient client = Feign.builder()
 *     .requestInterceptor(new UserFeignClientConfig().keycloakAuthInterceptor())
 *     .target(MyFeignClient.class, "http://example.com");
 * </pre>
 *
 * <p>Note: Ensure that the required properties for Keycloak integration are configured
 * in the application properties file.</p>
 *
 * @author
 * @version 1.0
 * @since 06/05/2025
 */
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

    /**
     * Creates a Feign `RequestInterceptor` for adding Keycloak authentication headers.
     *
     * <p>This interceptor ensures that each outgoing request includes a valid Bearer token
     * in the Authorization header. The token is fetched and cached until it expires.</p>
     *
     * @return the configured `RequestInterceptor`
     */
    @Bean
    public RequestInterceptor keycloakAuthInterceptor() {
        return requestTemplate -> {
            if (cachedToken == null || System.currentTimeMillis() > tokenExpiryTime) {
                cachedToken = fetchAccessToken();
            }
            requestTemplate.header("Authorization", "Bearer " + cachedToken);
        };
    }

    /**
     * Fetches an access token from Keycloak.
     *
     * <p>This method uses the client credentials grant type to authenticate with Keycloak
     * and retrieve an access token. The token is cached along with its expiry time.</p>
     *
     * @return the access token as a string
     */
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
