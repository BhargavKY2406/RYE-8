package com.restaurant.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO for incoming order placement requests.
 * <p>
 * The frontend sends this JSON payload when the user checks out.
 * It contains the restaurant ID, payment method, and a list of
 * cart items (menu ID + quantity). The service layer computes
 * prices and totals server-side for security.
 *
 * @param restaurantId  the restaurant the order is placed at
 * @param paymentMethod the chosen payment method (e.g. "UPI", "CASH_ON_DELIVERY")
 * @param items         the list of cart line-items
 */
public record OrderRequestDTO(

        @NotNull(message = "Restaurant ID is required")
        Long restaurantId,

        @NotNull(message = "Payment method is required")
        String paymentMethod,

        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemRequestDTO> items
) {}
