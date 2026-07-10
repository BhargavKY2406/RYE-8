package com.restaurant.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for user login requests.
 * <p>
 * Contains only the credentials needed for authentication.
 *
 * @param username the user's username
 * @param password the user's plaintext password (verified against the BCrypt hash)
 */
public record UserLoginDTO(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {}
