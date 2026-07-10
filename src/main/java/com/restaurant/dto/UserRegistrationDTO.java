package com.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for user registration requests.
 * <p>
 * Carries only the fields the frontend sends during sign-up.
 * Validated at the controller layer with Bean Validation annotations.
 *
 * @param username the desired username (3-50 characters)
 * @param password the desired password (minimum 6 characters)
 * @param email    a valid email address
 * @param address  optional delivery address
 */
public record UserRegistrationDTO(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid email address")
        String email,

        String address
) {}
