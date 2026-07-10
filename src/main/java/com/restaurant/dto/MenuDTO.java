package com.restaurant.dto;

import java.math.BigDecimal;

/**
 * DTO for transferring menu item data to the frontend.
 * <p>
 * Includes the parent restaurant's ID so the frontend can
 * associate menu items with their restaurant without a nested object.
 *
 * @param menuId       the menu item's primary key
 * @param restaurantId the owning restaurant's primary key
 * @param itemName     the display name of the menu item
 * @param description  a short description of the dish
 * @param price        the price per unit
 * @param isAvailable  whether the item is currently available
 * @param imagePath    relative or absolute path to the item's image
 */
public record MenuDTO(
        Long menuId,
        Long restaurantId,
        String restaurantName,
        String itemName,
        String description,
        BigDecimal price,
        Boolean isAvailable,
        Boolean isBestDish,
        String imagePath
) {}
