package com.restaurant.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Review")





public class Review {

    public Review() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewID")
    private Long reviewId;

    @Column(name = "Rating", nullable = false)
    private Integer rating; // 1 to 5

    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // A review is written by one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private AppUser user;

    // A review belongs to one restaurant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RestaurantID", nullable = false)
    private Restaurant restaurant;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // --- Generated Getters and Setters ---

    public Long getReviewId() {
        return this.reviewId;
    }
    
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getRating() {
        return this.rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    // --- Generated Builder ---
    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }
    
    public static class ReviewBuilder {
        private Long reviewId;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;
        private AppUser user;
        private Restaurant restaurant;


        public ReviewBuilder reviewId(Long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public ReviewBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReviewBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewBuilder user(AppUser user) {
            this.user = user;
            return this;
        }

        public ReviewBuilder restaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public Review build() {
            Review instance = new Review();
        instance.setReviewId(this.reviewId);
        instance.setRating(this.rating);
        instance.setComment(this.comment);
        instance.setCreatedAt(this.createdAt);
        instance.setUser(this.user);
        instance.setRestaurant(this.restaurant);

            return instance;
        }
    }
}
