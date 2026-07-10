package com.restaurant.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity mapped to the {@code OrderTable} table.
 * <p>
 * Represents a single order placed by a {@link AppUser} at a {@link Restaurant}.
 * The order aggregates multiple {@link OrderItem} line-items and tracks
 * the total amount, status, and payment method.
 * <p>
 * Note: The table is named "OrderTable" (not "Order") because ORDER is a
 * reserved keyword in SQL.
 * <p>
 * Relationships:
 * <ul>
 *   <li>Many-to-One with {@link AppUser} - many orders belong to one user.</li>
 *   <li>Many-to-One with {@link Restaurant} - many orders belong to one restaurant.</li>
 *   <li>One-to-Many with {@link OrderItem} - one order contains many line-items.</li>
 * </ul>
 */
@Entity
@Table(name = "OrderTable")





public class OrderTable {

    public OrderTable() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Long orderId;

    @Column(name = "OrderDate", nullable = false, updatable = false)
    private LocalDateTime orderDate;

    /** The computed total of all line-item subtotals. */
    @Column(name = "TotalAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 20)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "PaymentMethod", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    // ---- Relationships ----

    /** The user who placed this order. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private AppUser user;

    /** The restaurant this order was placed at. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RestaurantID", nullable = false)
    private Restaurant restaurant;

    /**
     * The individual line-items (menu item + quantity) that make up this order.
     * {@code CascadeType.ALL} ensures OrderItem rows are persisted/removed
     * together with the parent order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    
    private List<OrderItem> orderItems = new ArrayList<>();

    // ---- Lifecycle Callbacks ----

    /** Automatically sets the order date before the entity is first persisted. */
    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }

    // ---- Helper Methods ----

    /**
     * Convenience method to add a line-item and maintain both sides
     * of the bidirectional relationship.
     *
     * @param item the OrderItem to add
     */
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    // --- Generated Getters and Setters ---

    public Long getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return this.status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public AppUser getUser() {
        return this.user;
    }
    
    public void setUser(AppUser user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }
    
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // --- Generated Builder ---
    public static OrderTableBuilder builder() {
        return new OrderTableBuilder();
    }
    
    public static class OrderTableBuilder {
        private Long orderId;
        private LocalDateTime orderDate;
        private BigDecimal totalAmount;
        private OrderStatus status;
        private PaymentMethod paymentMethod;
        private AppUser user;
        private Restaurant restaurant;
        private List<OrderItem> orderItems;


        public OrderTableBuilder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderTableBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderTableBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderTableBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderTableBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public OrderTableBuilder user(AppUser user) {
            this.user = user;
            return this;
        }

        public OrderTableBuilder restaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public OrderTableBuilder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public OrderTable build() {
            OrderTable orderTable = new OrderTable();
            orderTable.setOrderId(this.orderId);
            orderTable.orderDate = this.orderDate; // direct access since no setter
            orderTable.setTotalAmount(this.totalAmount);
            orderTable.setStatus(this.status);
            orderTable.setPaymentMethod(this.paymentMethod);
            orderTable.setUser(this.user);
            orderTable.setRestaurant(this.restaurant);
            orderTable.setOrderItems(this.orderItems != null ? this.orderItems : new ArrayList<>());
            return orderTable;
        }
    }
}
