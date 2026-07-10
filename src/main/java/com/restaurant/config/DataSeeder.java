package com.restaurant.config;

import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.Review;
import com.restaurant.repository.MenuRepository;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final com.restaurant.repository.AppUserRepository userRepository;

    public DataSeeder(RestaurantRepository restaurantRepository, MenuRepository menuRepository, ReviewRepository reviewRepository, com.restaurant.repository.AppUserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running Data Seeder...");

        // 1. Seed Top Restaurants
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants found to seed.");
            return;
        }

        boolean tagsAdded = false;
        for (int i = 0; i < Math.min(3, restaurants.size()); i++) {
            Restaurant r = restaurants.get(i);
            if (r.getIsTopRestaurant() == null || !r.getIsTopRestaurant()) {
                r.setIsTopRestaurant(true);
                restaurantRepository.save(r);
                tagsAdded = true;
            }
        }

        // 2. Seed Best Dishes
        List<Menu> menus = menuRepository.findAll();
        for (int i = 0; i < Math.min(5, menus.size()); i++) {
            Menu m = menus.get(i);
            if (m.getIsBestDish() == null || !m.getIsBestDish()) {
                m.setIsBestDish(true);
                menuRepository.save(m);
                tagsAdded = true;
            }
        }

        // 3. Seed Reviews
        if (reviewRepository.count() == 0) {
            
            String[] indianNames = {
                "Aarav Sharma", "Priya Patel", "Rohan Gupta", "Ananya Singh", "Vivaan Desai", 
                "Diya Reddy", "Aditya Iyer", "Kavya Menon", "Kabir Nair", "Neha Joshi",
                "Arjun Verma", "Sanya Kapoor", "Rahul Choudhury", "Meera Rao", "Vikram Malhotra"
            };

            List<com.restaurant.entity.AppUser> generatedUsers = new ArrayList<>();
            for (String name : indianNames) {
                com.restaurant.entity.AppUser u = userRepository.findByUsername(name).orElseGet(() -> {
                    com.restaurant.entity.AppUser newUser = new com.restaurant.entity.AppUser();
                    newUser.setUsername(name);
                    newUser.setEmail(name.replaceAll("\\s+", "").toLowerCase() + "@example.com");
                    newUser.setPassword("password");
                    newUser.setRole(com.restaurant.entity.Role.CUSTOMER);
                    return userRepository.save(newUser);
                });
                generatedUsers.add(u);
            }

            String[] comments = {
                "Absolutely exquisite! The flavors were incredibly balanced and the presentation was a work of art.",
                "A truly Michelin-star experience. The spices were perfectly blended.",
                "Exceptional service and phenomenal food. Will definitely be ordering again.",
                "The best fine dining delivery I have ever experienced. Highly recommended!",
                "Stunning attention to detail. Every bite was a revelation.",
                "We booked a table for our anniversary and it was the most romantic and delicious evening we've ever had.",
                "The ambiance is just as amazing as the food. Five stars without a doubt.",
                "Prompt delivery, perfectly packaged, and still piping hot. A quality experience at home.",
                "The chef's tasting menu is a must-try! Exceeded all my expectations.",
                "Authentic taste that reminds me of home, but with a modern twist.",
                "The biryani was out of this world. Perfectly cooked rice and tender meat.",
                "One of the finest culinary experiences in the city. The appetizers alone are worth the price.",
                "Highly professional staff and the food quality is consistently top-notch.",
                "A delightful fusion of traditional recipes and modern presentation."
            };
            
            Random rand = new Random();
            for (Restaurant r : restaurants) {
                // Add 4 reviews per restaurant
                for (int i = 0; i < 4; i++) {
                    com.restaurant.entity.AppUser randomUser = generatedUsers.get(rand.nextInt(generatedUsers.size()));
                    Review rev = Review.builder()
                        .restaurant(r)
                        .user(randomUser)
                        .rating(4 + rand.nextInt(2))
                        .comment(comments[rand.nextInt(comments.length)])
                        .createdAt(LocalDateTime.now().minusDays(rand.nextInt(60)).minusHours(rand.nextInt(24)))
                        .build();
                    reviewRepository.save(rev);
                }
            }
            System.out.println("Reviews seeded successfully!");
        }

        if (tagsAdded) {
            System.out.println("Tags seeded successfully!");
        }
    }
}
