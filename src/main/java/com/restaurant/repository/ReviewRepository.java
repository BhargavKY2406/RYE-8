package com.restaurant.repository;

import com.restaurant.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRestaurant_RestaurantIdOrderByCreatedAtDesc(Long restaurantId);
}
