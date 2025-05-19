package com.ideas2it.training.patient.security;

/**
 * Utility class for managing tokens in a thread-local context.
 *
 * <p>This class provides methods to set, get, and clear tokens stored in a
 * thread-local variable. It is primarily used to manage authentication tokens
 * for the current thread during the request lifecycle.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
public class TokenContextHolder {

    /**
     * ThreadLocal variable to store the token for the current thread.
     */
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    /**
     * Retrieves the token stored in the current thread's context.
     *
     * @return the token, or null if no token is set
     */
    public static String getToken() {
        return tokenHolder.get();
    }

    /**
     * Sets the token in the current thread's context.
     *
     * @param token the token to set
     */
    public static void setToken(String token) {
        tokenHolder.set(token);
    }

    /**
     * Clears the token from the current thread's context.
     */
    public static void clear() {
        tokenHolder.remove();
    }
}