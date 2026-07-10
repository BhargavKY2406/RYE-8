package com.restaurant.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity mapped to the {@code AppUser} table.
 * <p>
 * Represents a registered user of the application.
 * Each user has a unique username and email, an encrypted password,
 * a role (CUSTOMER or ADMIN), and an optional delivery address.
 * <p>
 * Relationships:
 * <ul>
 *   <li>One-to-Many with {@link OrderTable} - a user can place many orders.</li>
 * </ul>
 */
@Entity
@Table(name = "AppUser")





public class AppUser {

    public AppUser() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false, length = 20)
    private Role role;

    @Column(name = "CreatedDate", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "LastLoginDate")
    private LocalDateTime lastLoginDate;

    // ---- Relationships ----

    /**
     * All orders placed by this user.
     * {@code mappedBy = "user"} means the OrderTable entity owns the FK.
     * {@code CascadeType.ALL} propagates persist/merge/remove to child orders.
     * {@code orphanRemoval = true} deletes order rows that are unlinked from the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    
    private List<OrderTable> orders = new ArrayList<>();

    // ---- Lifecycle Callbacks ----

    /**
     * Automatically sets the creation timestamp before the entity is first persisted.
     */
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    // --- Generated Getters and Setters ---

    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastLoginDate() {
        return this.lastLoginDate;
    }
    
    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public List<OrderTable> getOrders() {
        return this.orders;
    }
    
    public void setOrders(List<OrderTable> orders) {
        this.orders = orders;
    }

    // --- Generated Builder ---
    public static AppUserBuilder builder() {
        return new AppUserBuilder();
    }
    
    public static class AppUserBuilder {
        private Long userId;
        private String username;
        private String password;
        private String email;
        private String address;
        private Role role;
        private LocalDateTime createdDate;
        private LocalDateTime lastLoginDate;
        private List<OrderTable> orders;


        public AppUserBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public AppUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AppUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AppUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AppUserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public AppUserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public AppUserBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public AppUserBuilder lastLoginDate(LocalDateTime lastLoginDate) {
            this.lastLoginDate = lastLoginDate;
            return this;
        }

        public AppUserBuilder orders(List<OrderTable> orders) {
            this.orders = orders;
            return this;
        }

        public AppUser build() {
            AppUser instance = new AppUser();
        instance.setUserId(this.userId);
        instance.setUsername(this.username);
        instance.setPassword(this.password);
        instance.setEmail(this.email);
        instance.setAddress(this.address);
        instance.setRole(this.role);
        instance.setCreatedDate(this.createdDate);
        instance.setLastLoginDate(this.lastLoginDate);
        instance.setOrders(this.orders);

            return instance;
        }
    }
}
