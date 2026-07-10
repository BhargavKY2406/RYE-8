package com.restaurant.controller;

import com.restaurant.dto.ApiResponseDTO;
import com.restaurant.dto.MenuDTO;
import com.restaurant.dto.RestaurantDTO;
import com.restaurant.dto.ReviewRequestDTO;
import com.restaurant.dto.ReviewResponseDTO;
import com.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for restaurant and menu browsing endpoints.
 * <p>
 * All endpoints are public (no authentication required) so that
 * users can browse restaurants and menus before logging in.
 * <p>
 * Endpoints:
 * <ul>
 *   <li>{@code GET /api/restaurants}                    - list all active restaurants</li>
 *   <li>{@code GET /api/restaurants/{id}}               - get a single restaurant</li>
 *   <li>{@code GET /api/restaurants/search?name=...}    - search by name</li>
 *   <li>{@code GET /api/restaurants/cuisine?type=...}   - filter by cuisine</li>
 *   <li>{@code GET /api/restaurants/{id}/menu}          - get available menu items</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Get all active restaurants.
     */
    @GetMapping("/top")
    public ResponseEntity<ApiResponseDTO<List<RestaurantDTO>>> getTopRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.getTopRestaurants();
        return ResponseEntity.ok(ApiResponseDTO.success("Top restaurants fetched successfully", restaurants));
    }

    @GetMapping("/menu/best")
    public ResponseEntity<ApiResponseDTO<List<MenuDTO>>> getBestDishes() {
        List<MenuDTO> dishes = restaurantService.getBestDishes();
        return ResponseEntity.ok(ApiResponseDTO.success("Best dishes fetched successfully", dishes));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RestaurantDTO>>> getAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.getAllActiveRestaurants();
        return ResponseEntity.ok(ApiResponseDTO.success("Restaurants retrieved", restaurants));
    }

    /**
     * Get a single restaurant by ID.
     *
     * @param id the restaurant's primary key
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RestaurantDTO>> getRestaurantById(@PathVariable Long id) {
        RestaurantDTO restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Restaurant retrieved", restaurant));
    }

    /**
     * Search active restaurants by name (partial, case-insensitive).
     *
     * @param name the search keyword
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<RestaurantDTO>>> searchRestaurants(
            @RequestParam String name) {
        List<RestaurantDTO> restaurants = restaurantService.searchRestaurantsByName(name);
        return ResponseEntity.ok(ApiResponseDTO.success("Search results", restaurants));
    }

    /**
     * Filter active restaurants by cuisine type.
     *
     * @param type the cuisine type (e.g. "Italian", "Indian")
     */
    @GetMapping("/cuisine")
    public ResponseEntity<ApiResponseDTO<List<RestaurantDTO>>> getRestaurantsByCuisine(
            @RequestParam String type) {
        List<RestaurantDTO> restaurants = restaurantService.getRestaurantsByCuisine(type);
        return ResponseEntity.ok(ApiResponseDTO.success("Filtered results", restaurants));
    }


/**
 * Get available menu items for a specific restaurant.
 *
 * @param id the restaurant's primary key
 */
@GetMapping("/{id}/menu")
public ResponseEntity<ApiResponseDTO<List<MenuDTO>>> getMenuItems(@PathVariable Long id) {
    List<MenuDTO> menu = restaurantService.getAvailableMenuItems(id);
    return ResponseEntity.ok(ApiResponseDTO.success("Menu items retrieved", menu));
}

/**
 * Add a new review for a restaurant.
 */
@PostMapping("/{id}/reviews")
public ResponseEntity<ApiResponseDTO<ReviewResponseDTO>> addReview(
        @PathVariable Long id,
        @Valid @RequestBody ReviewRequestDTO dto,
        HttpSession session) {
    
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        throw new IllegalStateException("You must be logged in to leave a review");
    }
    
    ReviewResponseDTO review = restaurantService.addReview(id, userId, dto);
    return ResponseEntity.ok(ApiResponseDTO.success("Review added successfully", review));
}

/**
 * Get all reviews for a restaurant.
 */
@GetMapping("/{id}/reviews")
public ResponseEntity<ApiResponseDTO<List<ReviewResponseDTO>>> getReviews(@PathVariable Long id) {
    List<ReviewResponseDTO> reviews = restaurantService.getReviewsForRestaurant(id);
    return ResponseEntity.ok(ApiResponseDTO.success("Reviews retrieved", reviews));
}
}
