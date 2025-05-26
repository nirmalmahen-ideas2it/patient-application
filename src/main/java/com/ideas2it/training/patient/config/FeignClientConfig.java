package com.ideas2it.training.patient.config;

import com.ideas2it.training.patient.security.TokenContextHolder;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Feign Client.
 * <p>
 * This class provides a custom Feign Request Interceptor to include the
 * Authorization header with a Bearer token in all outgoing Feign client requests.
 * The token is retrieved from the {@link TokenContextHolder}.
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Configuration
public class FeignClientConfig {

    /**
     * Bean definition for the Feign Request Interceptor.
     * <p>
     * This interceptor adds the Authorization header to Feign client requests
     * if a valid token is available in the {@link TokenContextHolder}.
     *
     * @return a {@link RequestInterceptor} that adds the Authorization header.
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Retrieve the token from the TokenContextHolder
            String token = TokenContextHolder.getToken();

            // If a token is present, add it to the Authorization header
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}