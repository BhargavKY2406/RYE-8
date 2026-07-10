package com.restaurant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    
    private String comment;

    // --- Generated Getters and Setters ---

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

    // --- Generated Builder ---
    public static ReviewRequestDTOBuilder builder() {
        return new ReviewRequestDTOBuilder();
    }
    
    public static class ReviewRequestDTOBuilder {
        private Integer rating;
        private String comment;


        public ReviewRequestDTOBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReviewRequestDTOBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewRequestDTO build() {
            ReviewRequestDTO instance = new ReviewRequestDTO();
        instance.setRating(this.rating);
        instance.setComment(this.comment);

            return instance;
        }
    }
}
