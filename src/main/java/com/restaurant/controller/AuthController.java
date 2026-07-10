package com.restaurant.controller;

import com.restaurant.dto.*;
import com.restaurant.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user authentication endpoints.
 * <p>
 * Uses {@link HttpSession} to track the authenticated user.
 * The user's ID is stored in the session after successful login
 * and removed on logout.
 * <p>
 * Endpoints:
 * <ul>
 *   <li>{@code POST /api/auth/register} - create a new account</li>
 *   <li>{@code POST /api/auth/login}    - authenticate and start a session</li>
 *   <li>{@code POST /api/auth/logout}   - invalidate the session</li>
 *   <li>{@code GET  /api/auth/me}       - get the currently logged-in user</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user account.
     *
     * @param dto     validated registration form data
     * @return 201 Created with the new user's profile
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> register(
            @Valid @RequestBody UserRegistrationDTO dto) {

        UserResponseDTO user = userService.registerUser(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Registration successful", user));
    }

    /**
     * Authenticate a user and store their ID in the HTTP session.
     *
     * @param dto     validated login credentials
     * @param session the HTTP session (auto-created by the servlet container)
     * @return 200 OK with the authenticated user's profile
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> login(
            @Valid @RequestBody UserLoginDTO dto,
            HttpSession session) {

        UserResponseDTO user = userService.loginUser(dto);

        // Store user ID in session for subsequent requests
        session.setAttribute("userId", user.userId());
        session.setAttribute("username", user.username());
        session.setAttribute("role", user.role());

        return ResponseEntity.ok(ApiResponseDTO.success("Login successful", user));
    }

    /**
     * Log out the current user by invalidating the session.
     *
     * @param session the current HTTP session
     * @return 200 OK with a confirmation message
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO<Void>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(ApiResponseDTO.success("Logged out successfully"));
    }

    /**
     * Get the profile of the currently authenticated user.
     * <p>
     * Returns 401 if no user is logged in (no userId in session).
     *
     * @param session the current HTTP session
     * @return 200 OK with the user's profile, or 401 Unauthorized
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.error("Not logged in"));
        }

        UserResponseDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponseDTO.success("User retrieved", user));
    }
}
