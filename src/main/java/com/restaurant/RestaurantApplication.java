
package com.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Restaurant Food Ordering Application.
 * <p>
 * {@code @SpringBootApplication} combines:
 * <ul>
 *   <li>{@code @Configuration}  - marks this class as a source of bean definitions</li>
 *   <li>{@code @EnableAutoConfiguration} - enables Spring Boot auto-configuration</li>
 *   <li>{@code @ComponentScan}  - scans the com.restaurant package tree for components</li>
 * </ul>
 */
@SpringBootApplication
public class RestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }
}
