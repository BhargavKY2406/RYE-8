package com.restaurant.dto;

import java.math.BigDecimal;

/**
 * DTO for transferring restaurant data to the frontend.
 * <p>
 * Flattens the entity into a simple data carrier without
 * exposing the bidirectional JPA relationships (Menu, OrderTable lists).
 *
 * @param restaurantId the restaurant's primary key
 * @param name         the restaurant's display name
 * @param cuisineType  the type of cuisine (e.g. "Italian", "Indian")
 * @param deliveryTime estimated delivery time in minutes
 * @param address      the restaurant's physical address
 * @param rating       average customer rating (e.g. 4.5)
 * @param isActive     whether the restaurant is currently accepting orders
 * @param imagePath    relative or absolute path to the restaurant's image
 */
public record RestaurantDTO(
        Long restaurantId,
        String name,
        String cuisineType,
        Integer deliveryTime,
        String address,
        BigDecimal rating,
        Boolean isActive,
        Boolean isTopRestaurant,
        String imagePath,
        java.util.List<String> menuItems
) {}
