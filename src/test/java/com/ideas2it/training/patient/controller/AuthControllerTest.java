package com.ideas2it.training.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideas2it.training.patient.config.SecurityConfig;
import com.ideas2it.training.patient.entity.LoginRequest;
import com.ideas2it.training.patient.entity.LoginResponse;
import com.ideas2it.training.patient.entity.LoginResult;
import com.ideas2it.training.patient.web.rest.controller.AuthController;
import com.ideas2it.training.patient.webclient.UserValidationClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {OAuth2ResourceServerAutoConfiguration.class})
@Import(SecurityConfig.class)
@WithMockUser(roles = "USER")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private UserValidationClient userValidationClient;

    @MockBean
    private JwtDecoder jwtDecoder;

    @BeforeEach
    public void setup() {
        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.hasClaim(any(String.class))).thenReturn(false);
        Mockito.when(jwtDecoder.decode(any(String.class))).thenReturn(jwt);
    }

    @Test
    void testAuthenticate_ValidCredentials() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("username", "password");
        String token = "validToken";
        LoginResponse loginResponse = LoginResponse.builder()
                .status(LoginResult.SUCCESS)
                .message("Validation successful")
                .build();

        ValueOperations<String, Object> valueOperations = Mockito.mock(ValueOperations.class);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.doNothing().when(valueOperations).set(eq(token), eq(true), eq(Duration.ofSeconds(300)));

        Mockito.when(userValidationClient.validateUser(any(LoginRequest.class))).thenReturn(loginResponse);

        // Act & Assert
        ResultActions response = mockMvc.perform(post("/api/authenticate")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.access_token").value(token));

    }

    @Test
    void testAuthenticate_InvalidCredentials() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("username", "wrongPassword");
        LoginResponse loginResponse = LoginResponse.builder()
                .status(LoginResult.FAILURE)
                .message("Invalid credentials")
                .build();

        Mockito.when(userValidationClient.validateUser(any(LoginRequest.class))).thenReturn(loginResponse);

        // Act & Assert
        mockMvc.perform(post("/api/authenticate")
                        .header("Authorization", "Bearer invalidToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Invalid credentials"));
    }

    @Test
    void testAuthenticate_MissingAuthorizationHeader() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("username", "password");

        // Act & Assert
        mockMvc.perform(post("/api/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAuthenticate_InternalServerError() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("username", "password");
        Mockito.when(userValidationClient.validateUser(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Internal server error"));

        // Act & Assert
        mockMvc.perform(post("/api/authenticate")
                        .header("Authorization", "Bearer validToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}