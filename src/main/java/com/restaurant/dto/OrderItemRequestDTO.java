package com.restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for a single cart line-item within an {@link OrderRequestDTO}.
 * <p>
 * Contains only the menu item ID and the desired quantity.
 * The service layer looks up the price from the database to prevent
 * price manipulation from the client.
 *
 * @param menuId   the primary key of the menu item being ordered
 * @param quantity the number of units (must be at least 1)
 */
public record OrderItemRequestDTO(

        @NotNull(message = "Menu item ID is required")
        Long menuId,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {}
