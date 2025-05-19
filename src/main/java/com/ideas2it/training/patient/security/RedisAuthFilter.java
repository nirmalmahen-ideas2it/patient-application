package com.ideas2it.training.patient.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for authenticating requests using Redis.
 *
 * <p>This filter intercepts requests to patient-related endpoints and validates
 * the presence and validity of a Bearer token in the Authorization header. The
 * token is checked against Redis to ensure it exists and is not expired.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Component
public class RedisAuthFilter extends OncePerRequestFilter {

    /**
     * RedisTemplate for interacting with Redis.
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Constructor for injecting the RedisTemplate dependency.
     *
     * @param redisTemplate the RedisTemplate to use for token validation
     */
    public RedisAuthFilter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Filters incoming requests to validate the Authorization token.
     *
     * <p>This method checks if the request is targeting patient-related endpoints.
     * If so, it validates the Bearer token in the Authorization header by checking
     * its existence in Redis. If the token is missing, invalid, or expired, the
     * request is rejected with a 401 Unauthorized status.</p>
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to pass the request/response to the next filter
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs during filtering
     */
    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/api/patients")) { // Only protect patient endpoints
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Missing or invalid Authorization header");
                return;
            }

            String token = authHeader.replace("Bearer ", "");
            Boolean exists = redisTemplate.hasKey(token);
            if (Boolean.FALSE.equals(exists)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized or expired session");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}