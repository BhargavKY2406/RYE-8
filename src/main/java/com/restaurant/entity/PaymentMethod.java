package com.restaurant.entity;

/**
 * Enum representing the payment method chosen for an order.
 * Maps to the 'PaymentMethod' ENUM column in the OrderTable.
 */
public enum PaymentMethod {
    CASH_ON_DELIVERY,
    CREDIT_CARD,
    DEBIT_CARD,
    UPI,
    NET_BANKING
}
