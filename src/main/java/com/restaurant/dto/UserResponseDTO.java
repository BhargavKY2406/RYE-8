package com.restaurant.dto;

import java.time.LocalDateTime;

/**
 * DTO returned to the frontend after successful authentication or profile fetch.
 * <p>
 * Deliberately excludes the password hash - never expose credentials in API responses.
 *
 * @param userId        the user's primary key
 * @param username      the user's unique username
 * @param email         the user's email address
 * @param address       the user's delivery address
 * @param role          the user's role (CUSTOMER / ADMIN)
 * @param createdDate   when the account was created
 * @param lastLoginDate when the user last logged in
 */
public record UserResponseDTO(
        Long userId,
        String username,
        String email,
        String address,
        String role,
        LocalDateTime createdDate,
        LocalDateTime lastLoginDate
) {}
