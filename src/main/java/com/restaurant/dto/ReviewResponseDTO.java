package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long reviewId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private String username;

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

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    // --- Generated Builder ---
    public static ReviewResponseDTOBuilder builder() {
        return new ReviewResponseDTOBuilder();
    }
    
    public static class ReviewResponseDTOBuilder {
        private Long reviewId;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;
        private String username;


        public ReviewResponseDTOBuilder reviewId(Long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public ReviewResponseDTOBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReviewResponseDTOBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewResponseDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewResponseDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ReviewResponseDTO build() {
            ReviewResponseDTO instance = new ReviewResponseDTO();
        instance.setReviewId(this.reviewId);
        instance.setRating(this.rating);
        instance.setComment(this.comment);
        instance.setCreatedAt(this.createdAt);
        instance.setUsername(this.username);

            return instance;
        }
    }
}
