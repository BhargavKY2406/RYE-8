package com.restaurant.entity;

/**
 * Enum representing the lifecycle status of an order.
 * Maps to the 'Status' ENUM column in the OrderTable.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
