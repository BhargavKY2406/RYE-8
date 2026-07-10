package com.restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO returned to the frontend after an order is placed, or when
 * fetching order history.
 * <p>
 * Includes the order header fields plus a nested list of
 * {@link OrderItemResponseDTO} line-items for full order detail.
 *
 * @param orderId        the order's primary key
 * @param userId         the ID of the user who placed the order
 * @param restaurantId   the restaurant the order was placed at
 * @param restaurantName the restaurant's display name (denormalized for convenience)
 * @param orderDate      when the order was placed
 * @param totalAmount    the computed total of all line-items
 * @param status         the current order status (e.g. "PENDING", "DELIVERED")
 * @param paymentMethod  the chosen payment method
 * @param items          the list of line-items in this order
 */
public record OrderResponseDTO(
        Long orderId,
        Long userId,
        Long restaurantId,
        String restaurantName,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        BigDecimal deliveryFee,
        BigDecimal appFee,
        String status,
        String paymentMethod,
        List<OrderItemResponseDTO> items
) {}
