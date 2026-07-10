package com.restaurant.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * JPA Entity mapped to the {@code OrderItem} table.
 * <p>
 * Represents a single line-item within an {@link OrderTable}.
 * Each row captures which {@link Menu} item was ordered, the quantity,
 * and the computed subtotal ({@code price - quantity}).
 * <p>
 * Relationships:
 * <ul>
 *   <li>Many-to-One with {@link OrderTable} - many line-items belong to one order.</li>
 *   <li>Many-to-One with {@link Menu} - each line-item references one menu item.</li>
 * </ul>
 */
@Entity
@Table(name = "OrderItem")





public class OrderItem {

    public OrderItem() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemID")
    private Long orderItemId;

    /** Number of units of the menu item ordered. */
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    /**
     * Subtotal for this line-item: {@code menu.price - quantity}.
     * Computed by the service layer before persistence.
     */
    @Column(name = "ItemTotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal itemTotal;

    // ---- Relationships ----

    /** The parent order this line-item belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false)
    private OrderTable order;

    /** The menu item that was ordered. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MenuID", nullable = false)
    private Menu menu;

    // --- Generated Getters and Setters ---

    public Long getOrderItemId() {
        return this.orderItemId;
    }
    
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getItemTotal() {
        return this.itemTotal;
    }
    
    public void setItemTotal(BigDecimal itemTotal) {
        this.itemTotal = itemTotal;
    }

    public OrderTable getOrder() {
        return this.order;
    }
    
    public void setOrder(OrderTable order) {
        this.order = order;
    }

    public Menu getMenu() {
        return this.menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    // --- Generated Builder ---
    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }
    
    public static class OrderItemBuilder {
        private Long orderItemId;
        private Integer quantity;
        private BigDecimal itemTotal;
        private OrderTable order;
        private Menu menu;


        public OrderItemBuilder orderItemId(Long orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public OrderItemBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder itemTotal(BigDecimal itemTotal) {
            this.itemTotal = itemTotal;
            return this;
        }

        public OrderItemBuilder order(OrderTable order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder menu(Menu menu) {
            this.menu = menu;
            return this;
        }

        public OrderItem build() {
            OrderItem instance = new OrderItem();
        instance.setOrderItemId(this.orderItemId);
        instance.setQuantity(this.quantity);
        instance.setItemTotal(this.itemTotal);
        instance.setOrder(this.order);
        instance.setMenu(this.menu);

            return instance;
        }
    }
}
