package com.restaurant.exception;

/**
 * Custom exception thrown when a duplicate resource is detected
 * (e.g. registering with an existing username or email).
 * <p>
 * Handled globally by {@link GlobalExceptionHandler} to return a 409 response.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
