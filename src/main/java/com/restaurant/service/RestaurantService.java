package com.restaurant.service;

import com.restaurant.dto.MenuDTO;
import com.restaurant.dto.RestaurantDTO;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepository;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.dto.ReviewRequestDTO;
import com.restaurant.dto.ReviewResponseDTO;
import com.restaurant.entity.AppUser;
import com.restaurant.entity.Review;
import com.restaurant.repository.AppUserRepository;
import com.restaurant.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service layer for restaurant and menu item operations.
 * <p>
 * Provides read-only queries for browsing active restaurants,
 * searching by name/cuisine, and viewing a restaurant's available menu.
 */
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final AppUserRepository appUserRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             MenuRepository menuRepository,
                             ReviewRepository reviewRepository,
                             AppUserRepository appUserRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.reviewRepository = reviewRepository;
        this.appUserRepository = appUserRepository;
    }

    // =====================================================================
    // Restaurant Operations
    // =====================================================================

    /**
     * Get all active restaurants.
     *
     * @return list of active restaurants as DTOs
     */
    @Transactional(readOnly = true)
    
    public List<RestaurantDTO> getTopRestaurants() {
        return restaurantRepository.findByIsTopRestaurantTrueAndIsActiveTrue().stream()
                .map(this::mapRestaurantToDTO)
                .filter(distinctByKey(RestaurantDTO::name))
                .collect(Collectors.toList());
    }

    public List<MenuDTO> getBestDishes() {
        return menuRepository.findByIsBestDishTrueAndIsAvailableTrue().stream()
                .map(this::mapMenuToDTO)
                .filter(distinctByKey(MenuDTO::itemName))
                .collect(Collectors.toList());
    }

    public List<RestaurantDTO> getAllActiveRestaurants() {
        return restaurantRepository.findByIsActive(true)
                .stream()
                .map(this::mapRestaurantToDTO)
                .filter(distinctByKey(RestaurantDTO::name))
                .collect(Collectors.toList());
    }

    /**
     * Get a single restaurant by ID.
     *
     * @param restaurantId the restaurant's primary key
     * @return the restaurant as a DTO
     * @throws ResourceNotFoundException if the restaurant does not exist
     */
    @Transactional(readOnly = true)
    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", restaurantId));
        return mapRestaurantToDTO(restaurant);
    }

    /**
     * Search active restaurants by name (partial, case-insensitive match).
     *
     * @param name the search keyword
     * @return list of matching restaurants as DTOs
     */
    @Transactional(readOnly = true)
    public List<RestaurantDTO> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCaseAndIsActive(name, true)
                .stream()
                .map(this::mapRestaurantToDTO)
                .filter(distinctByKey(RestaurantDTO::name))
                .collect(Collectors.toList());
    }

    /**
     * Filter active restaurants by cuisine type (exact, case-insensitive match).
     *
     * @param cuisineType the cuisine type to filter by
     * @return list of matching restaurants as DTOs
     */
    @Transactional(readOnly = true)
    public List<RestaurantDTO> getRestaurantsByCuisine(String cuisineType) {
        return restaurantRepository.findByCuisineTypeIgnoreCaseAndIsActive(cuisineType, true)
                .stream()
                .map(this::mapRestaurantToDTO)
                .filter(distinctByKey(RestaurantDTO::name))
                .collect(Collectors.toList());
    }

    // =====================================================================
    // Menu Operations
    // =====================================================================

    /**
     * Get all available menu items for a specific restaurant.
     * Only items with {@code isAvailable = true} are returned.
     *
     * @param restaurantId the restaurant's primary key
     * @return list of available menu items as DTOs
     * @throws ResourceNotFoundException if the restaurant does not exist
     */
    @Transactional(readOnly = true)
    public List<MenuDTO> getAvailableMenuItems(Long restaurantId) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant", restaurantId);
        }

        return menuRepository.findByRestaurant_RestaurantIdAndIsAvailable(restaurantId, true)
                .stream()
                .map(this::mapMenuToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all menu items for a restaurant (including unavailable).
     * Useful for admin views.
     *
     * @param restaurantId the restaurant's primary key
     * @return list of all menu items as DTOs
     */
    @Transactional(readOnly = true)
    public List<MenuDTO> getAllMenuItems(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant", restaurantId);
        }

        return menuRepository.findByRestaurant_RestaurantId(restaurantId)
                .stream()
                .map(this::mapMenuToDTO)
                .collect(Collectors.toList());
    }

    // =====================================================================
    // Review Operations
    // =====================================================================

    @Transactional
    public ReviewResponseDTO addReview(Long restaurantId, Long userId, ReviewRequestDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", restaurantId));
        
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
                
        Review review = Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .restaurant(restaurant)
                .user(user)
                .build();
                
        review = reviewRepository.save(review);
        
        // Recalculate average rating
        List<Review> allReviews = reviewRepository.findByRestaurant_RestaurantIdOrderByCreatedAtDesc(restaurantId);
        double average = allReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
                
        restaurant.setRating(BigDecimal.valueOf(average).setScale(1, RoundingMode.HALF_UP));
        restaurantRepository.save(restaurant);
        
        return mapReviewToDTO(review);
    }
    
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsForRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant", restaurantId);
        }
        
        return reviewRepository.findByRestaurant_RestaurantIdOrderByCreatedAtDesc(restaurantId)
                .stream()
                .map(this::mapReviewToDTO)
                .collect(Collectors.toList());
    }

    // =====================================================================
    // Entity - DTO Mapping
    // =====================================================================

    /**
     * Convert a {@link Restaurant} entity to a {@link RestaurantDTO}.
     */
    private RestaurantDTO mapRestaurantToDTO(Restaurant restaurant) {
        java.util.List<String> items = java.util.Collections.emptyList();
        if (restaurant.getMenuItems() != null) {
            items = restaurant.getMenuItems().stream()
                    .map(com.restaurant.entity.Menu::getItemName)
                    .toList();
        }
        return new RestaurantDTO(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getCuisineType(),
                restaurant.getDeliveryTime(),
                restaurant.getAddress(),
                restaurant.getRating(),
                restaurant.getIsActive(),
                restaurant.getIsTopRestaurant(),
                restaurant.getImagePath(),
                items
        );
    }

    /**
     * Convert a {@link Menu} entity to a {@link MenuDTO}.
     */
    private MenuDTO mapMenuToDTO(Menu menu) {
        return new MenuDTO(
                menu.getMenuId(),
                menu.getRestaurant().getRestaurantId(),
                menu.getRestaurant().getName(),
                menu.getItemName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getIsAvailable(),
                menu.getIsBestDish(),
                menu.getImagePath()
        );
    }
    
    private ReviewResponseDTO mapReviewToDTO(Review review) {
        return ReviewResponseDTO.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .username(review.getUser().getUsername())
                .build();
    }

    /**
     * Helper to filter out duplicates by a specific key.
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
