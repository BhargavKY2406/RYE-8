package com.restaurant.exception;

/**
 * Custom exception thrown when a requested resource is not found.
 * <p>
 * Handled globally by {@link GlobalExceptionHandler} to return a 404 response.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with ID: " + id);
    }
}
