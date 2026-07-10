package com.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security configuration.
 * <p>
 * Since this application uses a session-based approach with a vanilla J
 * frontend (no OAuth/JWT), the security configuration:
 * <ul>
 *   <li>Permits all requests to static resources and public API endpoints
 *       (registration, login, restaurant/menu browsing).</li>
 *   <li>Requires authentication only for order-related endpoints.</li>
 *   <li>Disables CSRF for simplicity with the REST API (the frontend uses
 *       Fetch API with JSON bodies, not HTML forms).</li>
 *   <li>Provides a {@link BCryptPasswordEncoder} bean for password hashing.</li>
 * </ul>
 * <p>
 * Authentication is handled manually in the controller layer (session-based)
 * rather than through Spring Security's built-in form login.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the HTTP security filter chain.
     * <p>
     * All endpoints are permitted because the app uses a simple session-based
     * auth managed by the controller (userId stored in HttpSession).
     * Spring Security is used here primarily for the PasswordEncoder bean
     * and as a foundation for future enhancements (e.g. JWT).
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * BCrypt password encoder bean.
     * <p>
     * BCrypt is the industry standard for password hashing - it automatically
     * generates a random salt and stores it within the hash, making rainbow
     * table attacks infeasible.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
