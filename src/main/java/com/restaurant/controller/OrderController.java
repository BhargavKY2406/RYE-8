package com.restaurant.controller;

import com.restaurant.dto.ApiResponseDTO;
import com.restaurant.dto.OrderRequestDTO;
import com.restaurant.dto.OrderResponseDTO;
import com.restaurant.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for order placement and order history endpoints.
 * <p>
 * All endpoints require the user to be logged in (userId must be
 * present in the HTTP session). Returns 401 if the session is missing.
 * <p>
 * Endpoints:
 * <ul>
 *   <li>{@code POST /api/orders}       - place a new order (checkout)</li>
 *   <li>{@code GET  /api/orders}       - get the logged-in user's order history</li>
 *   <li>{@code GET  /api/orders/{id}}  - get a specific order by ID</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Place a new order from the user's cart.
     * <p>
     * Expects a JSON payload with restaurantId, paymentMethod, and
     * a list of items (menuId + quantity). The service layer handles
     * price lookup, total computation, and persistence.
     *
     * @param dto     validated order request
     * @param session the current HTTP session (must contain userId)
     * @return 201 Created with the full order details
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> placeOrder(
            @Valid @RequestBody OrderRequestDTO dto,
            HttpSession session) {

        Long userId = getAuthenticatedUserId(session);

        OrderResponseDTO order = orderService.placeOrder(userId, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Order placed successfully", order));
    }

    /**
     * Get the order history for the currently logged-in user.
     *
     * @param session the current HTTP session (must contain userId)
     * @return 200 OK with the list of orders (most recent first)
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<OrderResponseDTO>>> getMyOrders(HttpSession session) {
        Long userId = getAuthenticatedUserId(session);

        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(ApiResponseDTO.success("Orders retrieved", orders));
    }

    /**
     * Get a specific order by its ID.
     *
     * @param id      the order's primary key
     * @param session the current HTTP session (must contain userId)
     * @return 200 OK with the order details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> getOrderById(
            @PathVariable Long id,
            HttpSession session) {

        // Ensure user is logged in
        getAuthenticatedUserId(session);

        OrderResponseDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Order retrieved", order));
    }

    /**
     * Trigger a background simulation of an order's progress.
     */
    @PostMapping("/{id}/simulate")
    public ResponseEntity<ApiResponseDTO<String>> simulateOrderProgress(
            @PathVariable Long id,
            HttpSession session) {
        
        getAuthenticatedUserId(session);
        orderService.simulateOrderProgress(id);
        
        return ResponseEntity.ok(ApiResponseDTO.success("Simulation started", "Order will progress every 10 seconds."));
    }

    // =====================================================================
    // Helper
    // =====================================================================

    /**
     * Extract the authenticated user's ID from the session.
     *
     * @param session the current HTTP session
     * @return the user's ID
     * @throws IllegalStateException if no user is logged in
     */
    private Long getAuthenticatedUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("You must be logged in to perform this action");
        }
        return userId;
    }
}
