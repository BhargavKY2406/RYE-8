package com.restaurant.repository;

import com.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Restaurant} entity.
 * <p>
 * Provides CRUD operations plus custom finders for browsing
 * active restaurants and filtering by cuisine type.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Find all restaurants that are currently active (accepting orders).
     *
     * @param isActive true to find active restaurants, false for inactive
     * @return list of matching restaurants
     */
    List<Restaurant> findByIsActive(Boolean isActive);

    /**
     * Find active restaurants filtered by cuisine type (case-insensitive).
     *
     * @param cuisineType the cuisine type to filter by
     * @param isActive    the active status
     * @return list of matching restaurants
     */
    List<Restaurant> findByCuisineTypeIgnoreCaseAndIsActive(String cuisineType, Boolean isActive);

    /**
     * Search active restaurants whose name contains the given keyword (case-insensitive).
     *
     * @param name     partial restaurant name to search for
     * @param isActive the active status
     * @return list of matching restaurants
     */
    List<Restaurant> findByNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive);

    List<Restaurant> findByIsTopRestaurantTrueAndIsActiveTrue();
}
