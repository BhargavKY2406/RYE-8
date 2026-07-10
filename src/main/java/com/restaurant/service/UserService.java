package com.restaurant.service;

import com.restaurant.dto.UserLoginDTO;
import com.restaurant.dto.UserRegistrationDTO;
import com.restaurant.dto.UserResponseDTO;
import com.restaurant.entity.AppUser;
import com.restaurant.entity.Role;
import com.restaurant.exception.DuplicateResourceException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.AppUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service layer for user authentication and account management.
 * <p>
 * Handles registration (with duplicate checks and password hashing),
 * login (with credential verification and last-login tracking),
 * and entity-to-DTO mapping.
 */
@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =====================================================================
    // Registration
    // =====================================================================

    /**
     * Register a new user account.
     * <ol>
     *   <li>Checks that the username and email are not already taken.</li>
     *   <li>Hashes the plaintext password with BCrypt.</li>
     *   <li>Persists the new {@link AppUser} entity with role CUSTOMER.</li>
     *   <li>Returns a safe {@link UserResponseDTO} (no password hash).</li>
     * </ol>
     *
     * @param dto the registration form data
     * @return the newly created user as a response DTO
     * @throws DuplicateResourceException if username or email is already taken
     */
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO dto) {
        // Duplicate checks
        if (userRepository.existsByUsername(dto.username())) {
            throw new DuplicateResourceException("Username '" + dto.username() + "' is already taken");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email '" + dto.email() + "' is already registered");
        }

        // Build and persist entity
        AppUser user = AppUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .email(dto.email())
                .address(dto.address())
                .role(Role.CUSTOMER)
                .build();

        AppUser saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    // =====================================================================
    // Login
    // =====================================================================

    /**
     * Authenticate a user by username and password.
     * <ol>
     *   <li>Looks up the user by username.</li>
     *   <li>Verifies the plaintext password against the stored BCrypt hash.</li>
     *   <li>Updates {@code lastLoginDate} on success.</li>
     *   <li>Returns a safe {@link UserResponseDTO}.</li>
     * </ol>
     *
     * @param dto the login credentials
     * @return the authenticated user as a response DTO
     * @throws ResourceNotFoundException if the username does not exist
     * @throws BadCredentialsException   if the password is incorrect
     */
    @Transactional
    public UserResponseDTO loginUser(UserLoginDTO dto) {
        AppUser user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + dto.username()));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Track last login timestamp
        user.setLastLoginDate(LocalDateTime.now());
        userRepository.save(user);

        return mapToDTO(user);
    }

    // =====================================================================
    // Lookup
    // =====================================================================

    /**
     * Retrieve a user by their primary key.
     *
     * @param userId the user's ID
     * @return the user as a response DTO
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return mapToDTO(user);
    }

    // =====================================================================
    // Entity - DTO Mapping
    // =====================================================================

    /**
     * Convert an {@link AppUser} entity to a {@link UserResponseDTO}.
     * Strips the password hash so it is never sent to the frontend.
     */
    private UserResponseDTO mapToDTO(AppUser user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAddress(),
                user.getRole().name(),
                user.getCreatedDate(),
                user.getLastLoginDate()
        );
    }
}
