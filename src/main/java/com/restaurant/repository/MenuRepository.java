package com.restaurant.repository;

import com.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Menu} entity.
 * <p>
 * Provides CRUD operations plus custom finders for retrieving
 * available menu items belonging to a specific restaurant.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * Find all menu items for a given restaurant.
     *
     * @param restaurantId the restaurant's primary key
     * @return list of all menu items (available and unavailable)
     */
    List<Menu> findByRestaurant_RestaurantId(Long restaurantId);

    /**
     * Find only available menu items for a given restaurant.
     * This is the primary query used by the customer-facing frontend.
     *
     * @param restaurantId the restaurant's primary key
     * @param isAvailable  true to fetch only available items
     * @return list of available menu items
     */
    List<Menu> findByRestaurant_RestaurantIdAndIsAvailable(Long restaurantId, Boolean isAvailable);

    List<Menu> findByIsBestDishTrueAndIsAvailableTrue();
}
