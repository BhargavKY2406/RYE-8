package com.restaurant.dto;

import java.math.BigDecimal;

/**
 * DTO for a single line-item within an {@link OrderResponseDTO}.
 * <p>
 * Denormalizes the menu item name and price so the frontend can
 * display full order details without additional API calls.
 *
 * @param orderItemId the line-item's primary key
 * @param menuId      the menu item's primary key
 * @param itemName    the display name of the menu item
 * @param quantity    the number of units ordered
 * @param price       the unit price at the time of order
 * @param itemTotal   the subtotal (price - quantity)
 */
public record OrderItemResponseDTO(
        Long orderItemId,
        Long menuId,
        String itemName,
        Integer quantity,
        BigDecimal price,
        BigDecimal itemTotal
) {}
