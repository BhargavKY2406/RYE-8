package com.restaurant.repository;

import com.restaurant.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link AppUser} entity.
 * <p>
 * Provides CRUD operations plus custom finders used by the
 * authentication and user-management services.
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Find a user by their unique username.
     * Used during login and by Spring Security's UserDetailsService.
     *
     * @param username the username to search for
     * @return an Optional containing the user, or empty if not found
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Find a user by their unique email address.
     * Used during registration to check for duplicate emails.
     *
     * @param email the email to search for
     * @return an Optional containing the user, or empty if not found
     */
    Optional<AppUser> findByEmail(String email);

    /**
     * Check whether a username is already taken.
     *
     * @param username the username to check
     * @return true if a user with the given username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check whether an email is already registered.
     *
     * @param email the email to check
     * @return true if a user with the given email exists
     */
    boolean existsByEmail(String email);
}
