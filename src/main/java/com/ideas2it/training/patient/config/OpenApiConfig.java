package com.ideas2it.training.patient.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI and Swagger UI with JWT support.
 *
 * <p>This class sets up the OpenAPI documentation for the Patient module, including
 * security configurations for JWT-based authentication. It also defines a grouped
 * API for better organization of endpoints in Swagger UI.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * OpenAPI openAPI = new OpenApiConfig().customOpenAPI();
 * GroupedOpenApi groupedApi = new OpenApiConfig().patientApi();
 * </pre>
 *
 * <p>Note: Ensure that the application is configured with proper JWT settings for
 * the security scheme to function correctly.</p>
 *
 * @author Alagu Nirmal Mahendran
 * @version 1.0
 * @since 06/05/2025
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    /**
     * Configures the OpenAPI documentation with security and general information.
     *
     * <p>This method sets up the API title, version, description, and security
     * requirements for JWT-based authentication.</p>
     *
     * @return the configured {@link OpenAPI} object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Patient API")
                .version("1.0")
                .description("API documentation for the Patient module"))
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }

    /**
     * Configures a grouped API for the Patient module.
     *
     * <p>This method organizes the endpoints related to the Patient module into
     * a separate group in Swagger UI for better clarity and navigation.</p>
     *
     * @return the configured {@link GroupedOpenApi} object
     */
    @Bean
    public GroupedOpenApi patientApi() {
        return GroupedOpenApi.builder()
            .group("patient")
            .packagesToScan("com.ideas2it.training.patient.web.rest.controller")
            .build();
    }
}
