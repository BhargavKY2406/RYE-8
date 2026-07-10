package com.restaurant.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity mapped to the {@code Restaurant} table.
 * <p>
 * Represents a restaurant registered on the platform.
 * Stores metadata such as cuisine type, estimated delivery time,
 * average rating, and an active/inactive flag.
 * <p>
 * Relationships:
 * <ul>
 *   <li>One-to-Many with {@link Menu} - a restaurant has many menu items.</li>
 *   <li>One-to-Many with {@link OrderTable} - a restaurant receives many orders.</li>
 * </ul>
 */
@Entity
@Table(name = "Restaurant")





public class Restaurant {

    public Restaurant() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RestaurantID")
    private Long restaurantId;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "CuisineType", length = 50)
    private String cuisineType;

    /** Estimated delivery time in minutes. */
    @Column(name = "DeliveryTime")
    private Integer deliveryTime;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    /** Average customer rating (e.g. 4.5 out of 5). */
    @Column(name = "Rating", precision = 3, scale = 1)
    private BigDecimal rating;

    /** Whether the restaurant is currently accepting orders. */
    @Column(name = "IsActive", nullable = false)
    
    private Boolean isActive = true;

    @Column(name = "ImagePath", length = 255)
    private String imagePath;

    @Column(name = "IsTopRestaurant", nullable = false)
    
    private Boolean isTopRestaurant = false;

    // ---- Relationships ----

    /** All menu items offered by this restaurant. */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    
    private List<Menu> menuItems = new ArrayList<>();

    /** All orders placed at this restaurant. */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    
    private List<OrderTable> orders = new ArrayList<>();

    // --- Generated Getters and Setters ---

    public Long getRestaurantId() {
        return this.restaurantId;
    }
    
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return this.cuisineType;
    }
    
    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Integer getDeliveryTime() {
        return this.deliveryTime;
    }
    
    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getRating() {
        return this.rating;
    }
    
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getImagePath() {
        return this.imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getIsTopRestaurant() {
        return this.isTopRestaurant;
    }
    
    public void setIsTopRestaurant(Boolean isTopRestaurant) {
        this.isTopRestaurant = isTopRestaurant;
    }

    public List<Menu> getMenuItems() {
        return this.menuItems;
    }
    
    public void setMenuItems(List<Menu> menuItems) {
        this.menuItems = menuItems;
    }

    public List<OrderTable> getOrders() {
        return this.orders;
    }
    
    public void setOrders(List<OrderTable> orders) {
        this.orders = orders;
    }

    // --- Generated Builder ---
    public static RestaurantBuilder builder() {
        return new RestaurantBuilder();
    }
    
    public static class RestaurantBuilder {
        private Long restaurantId;
        private String name;
        private String cuisineType;
        private Integer deliveryTime;
        private String address;
        private BigDecimal rating;
        private Boolean isActive;
        private String imagePath;
        private Boolean isTopRestaurant;
        private List<Menu> menuItems;
        private List<OrderTable> orders;


        public RestaurantBuilder restaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public RestaurantBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RestaurantBuilder cuisineType(String cuisineType) {
            this.cuisineType = cuisineType;
            return this;
        }

        public RestaurantBuilder deliveryTime(Integer deliveryTime) {
            this.deliveryTime = deliveryTime;
            return this;
        }

        public RestaurantBuilder address(String address) {
            this.address = address;
            return this;
        }

        public RestaurantBuilder rating(BigDecimal rating) {
            this.rating = rating;
            return this;
        }

        public RestaurantBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public RestaurantBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public RestaurantBuilder isTopRestaurant(Boolean isTopRestaurant) {
            this.isTopRestaurant = isTopRestaurant;
            return this;
        }

        public RestaurantBuilder menuItems(List<Menu> menuItems) {
            this.menuItems = menuItems;
            return this;
        }

        public RestaurantBuilder orders(List<OrderTable> orders) {
            this.orders = orders;
            return this;
        }

        public Restaurant build() {
            Restaurant instance = new Restaurant();
        instance.setRestaurantId(this.restaurantId);
        instance.setName(this.name);
        instance.setCuisineType(this.cuisineType);
        instance.setDeliveryTime(this.deliveryTime);
        instance.setAddress(this.address);
        instance.setRating(this.rating);
        instance.setIsActive(this.isActive);
        instance.setImagePath(this.imagePath);
        instance.setIsTopRestaurant(this.isTopRestaurant);
        instance.setMenuItems(this.menuItems);
        instance.setOrders(this.orders);

            return instance;
        }
    }
}
