package com.restaurant.dto;

import java.time.LocalDateTime;

/**
 * A generic API response wrapper returned by all REST endpoints.
 * <p>
 * Provides a consistent response envelope so the frontend can
 * always expect the same top-level structure:
 * <pre>
 * {
 *   "success": true,
 *   "message": "Operation completed",
 *   "data": { ... },
 *   "timestamp": "2026-07-06T10:00:00"
 * }
 * </pre>
 *
 * @param <T> the type of the payload in the {@code data} field
 */
public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // ---- Constructors ----

    public ApiResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // ---- Static Factory Methods ----

    /**
     * Create a successful response with a message and payload.
     *
     * @param message human-readable success message
     * @param data    the response payload
     * @param <T>     the payload type
     * @return a new ApiResponseDTO
     */
    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<>(true, message, data);
    }

    /**
     * Create a successful response with only a message (no payload).
     *
     * @param message human-readable success message
     * @param <T>     the payload type (will be null)
     * @return a new ApiResponseDTO
     */
    public static <T> ApiResponseDTO<T> success(String message) {
        return new ApiResponseDTO<>(true, message, null);
    }

    /**
     * Create an error response with a message.
     *
     * @param message human-readable error description
     * @param <T>     the payload type (will be null)
     * @return a new ApiResponseDTO
     */
    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<>(false, message, null);
    }

    /**
     * Create an error response with a message and additional data
     * (e.g. validation error details).
     *
     * @param message human-readable error description
     * @param data    additional error details
     * @param <T>     the payload type
     * @return a new ApiResponseDTO
     */
    public static <T> ApiResponseDTO<T> error(String message, T data) {
        return new ApiResponseDTO<>(false, message, data);
    }

    // ---- Getters & Setters ----

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
