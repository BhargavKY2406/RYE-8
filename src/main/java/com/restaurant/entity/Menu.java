package com.restaurant.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity mapped to the {@code Menu} table.
 * <p>
 * Represents a single menu item offered by a {@link Restaurant}.
 * Each item has a name, description, price, availability flag,
 * and an optional image path for display on the frontend.
 * <p>
 * Relationships:
 * <ul>
 *   <li>Many-to-One with {@link Restaurant} - many menu items belong to one restaurant.</li>
 *   <li>One-to-Many with {@link OrderItem} - a menu item can appear in many order line-items.</li>
 * </ul>
 */
@Entity
@Table(name = "Menu")





public class Menu {

    public Menu() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuID")
    private Long menuId;

    @Column(name = "ItemName", nullable = false, length = 100)
    private String itemName;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    /** Price of the item (up to 8 digits, 2 decimal places). */
    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** Whether the item is currently available for ordering. */
    @Column(name = "IsAvailable", nullable = false)
    
    private Boolean isAvailable = true;

    @Column(name = "ImagePath", length = 255)
    private String imagePath;

    @Column(name = "IsBestDish", nullable = false)
    
    private Boolean isBestDish = false;

    // ---- Relationships ----

    /**
     * The restaurant that offers this menu item.
     * {@code @JoinColumn} maps to the FK column 'RestaurantID' in the Menu table.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RestaurantID", nullable = false)
    private Restaurant restaurant;

    /** All order line-items that reference this menu item. */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    
    private List<OrderItem> orderItems = new ArrayList<>();

    // --- Generated Getters and Setters ---

    public Long getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getItemName() {
        return this.itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }
    
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getImagePath() {
        return this.imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getIsBestDish() {
        return this.isBestDish;
    }
    
    public void setIsBestDish(Boolean isBestDish) {
        this.isBestDish = isBestDish;
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
    public static MenuBuilder builder() {
        return new MenuBuilder();
    }
    
    public static class MenuBuilder {
        private Long menuId;
        private String itemName;
        private String description;
        private BigDecimal price;
        private Boolean isAvailable;
        private String imagePath;
        private Boolean isBestDish;
        private Restaurant restaurant;
        private List<OrderItem> orderItems;


        public MenuBuilder menuId(Long menuId) {
            this.menuId = menuId;
            return this;
        }

        public MenuBuilder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public MenuBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MenuBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MenuBuilder isAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public MenuBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public MenuBuilder isBestDish(Boolean isBestDish) {
            this.isBestDish = isBestDish;
            return this;
        }

        public MenuBuilder restaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public MenuBuilder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Menu build() {
            Menu instance = new Menu();
        instance.setMenuId(this.menuId);
        instance.setItemName(this.itemName);
        instance.setDescription(this.description);
        instance.setPrice(this.price);
        instance.setIsAvailable(this.isAvailable);
        instance.setImagePath(this.imagePath);
        instance.setIsBestDish(this.isBestDish);
        instance.setRestaurant(this.restaurant);
        instance.setOrderItems(this.orderItems);

            return instance;
        }
    }
}
