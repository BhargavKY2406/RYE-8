package com.restaurant.service;

import com.restaurant.dto.*;
import com.restaurant.entity.*;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for order placement and order history retrieval.
 * <p>
 * Contains the core checkout business logic:
 * <ol>
 *   <li>Validates the user, restaurant, and every menu item in the cart.</li>
 *   <li>Computes each line-item subtotal ({@code price - quantity}) server-side.</li>
 *   <li>Sums all subtotals to get the order total.</li>
 *   <li>Persists the {@link OrderTable} header and all {@link OrderItem} rows
 *       in a single transaction.</li>
 * </ol>
 */
@Service
public class OrderService {

    private final OrderTableRepository orderTableRepository;
    private final AppUserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public OrderService(OrderTableRepository orderTableRepository,
                        AppUserRepository userRepository,
                        RestaurantRepository restaurantRepository,
                        MenuRepository menuRepository) {
        this.orderTableRepository = orderTableRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    // =====================================================================
    // Place Order
    // =====================================================================

    /**
     * Place a new order from the user's cart.
     * <p>
     * This method performs the following steps inside a single transaction:
     * <ol>
     *   <li>Look up the authenticated user by ID.</li>
     *   <li>Look up the restaurant by ID and verify it is active.</li>
     *   <li>Parse the payment method from the request string.</li>
     *   <li>For each cart item:
     *       <ul>
     *         <li>Look up the menu item and verify it is available.</li>
     *         <li>Compute {@code itemTotal = price - quantity} (server-side).</li>
     *         <li>Create an {@link OrderItem} entity.</li>
     *       </ul>
     *   </li>
     *   <li>Sum all item totals to compute the order's {@code totalAmount}.</li>
     *   <li>Persist the order (cascades to order items).</li>
     *   <li>Return the order as a response DTO.</li>
     * </ol>
     *
     * @param userId the authenticated user's ID
     * @param dto    the checkout request containing restaurant ID, payment method, and cart items
     * @return the newly created order as a response DTO
     * @throws ResourceNotFoundException if user, restaurant, or any menu item is not found
     * @throws IllegalArgumentException  if the restaurant is inactive, a menu item is unavailable,
     *                                    or the payment method is invalid
     */
    @Transactional
    public OrderResponseDTO placeOrder(Long userId, OrderRequestDTO dto) {

        // 1. Validate user
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        // 2. Validate restaurant
        Restaurant restaurant = restaurantRepository.findById(dto.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", dto.restaurantId()));

        if (!restaurant.getIsActive()) {
            throw new IllegalArgumentException("Restaurant '" + restaurant.getName() + "' is currently not accepting orders");
        }

        // 3. Parse payment method
        PaymentMethod paymentMethod;
        try {
            paymentMethod = PaymentMethod.valueOf(dto.paymentMethod());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid payment method: " + dto.paymentMethod());
        }

        // 4. Build the order header
        OrderTable order = OrderTable.builder()
                .user(user)
                .restaurant(restaurant)
                .status(OrderStatus.PENDING)
                .paymentMethod(paymentMethod)
                .totalAmount(BigDecimal.ZERO) // will be computed below
                .build();

        // 5. Process each cart item
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.items()) {
            Menu menu = menuRepository.findById(itemDTO.menuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item", itemDTO.menuId()));

            if (!menu.getIsAvailable()) {
                throw new IllegalArgumentException("Menu item '" + menu.getItemName() + "' is currently unavailable");
            }

            // Verify the menu item belongs to the correct restaurant
            if (!menu.getRestaurant().getRestaurantId().equals(dto.restaurantId())) {
                throw new IllegalArgumentException(
                        "Menu item '" + menu.getItemName() + "' does not belong to the selected restaurant");
            }

            // Compute subtotal server-side (prevents price manipulation)
            BigDecimal itemTotal = menu.getPrice().multiply(BigDecimal.valueOf(itemDTO.quantity()));

            OrderItem orderItem = OrderItem.builder()
                    .menu(menu)
                    .quantity(itemDTO.quantity())
                    .itemTotal(itemTotal)
                    .build();

            order.addOrderItem(orderItem);
            totalAmount = totalAmount.add(itemTotal);
        }

        // 6. Set computed total and persist
        order.setTotalAmount(totalAmount);
        OrderTable savedOrder = orderTableRepository.save(order);

        return mapToDTO(savedOrder);
    }

    // =====================================================================
    // Order History
    // =====================================================================

    /**
     * Get all orders for a specific user, sorted by most recent first.
     *
     * @param userId the user's primary key
     * @return list of the user's orders as response DTOs
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", userId);
        }

        return orderTableRepository.findByUser_UserIdOrderByOrderDateDesc(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a single order by its ID.
     *
     * @param orderId the order's primary key
     * @return the order as a response DTO
     * @throws ResourceNotFoundException if the order does not exist
     */
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {
        OrderTable order = orderTableRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        return mapToDTO(order);
    }

    // =====================================================================
    // Order Simulation (Real-Time Tracking)
    // =====================================================================

    /**
     * Triggers a background thread to simulate an order progressing through statuses.
     */
    public void simulateOrderProgress(Long orderId) {
        java.util.concurrent.CompletableFuture.runAsync(() -> {
            try {
                // PENDING -> PREPARING
                Thread.sleep(10000);
                updateOrderStatus(orderId, OrderStatus.PREPARING);
                
                // PREPARING -> OUT_FOR_DELIVERY
                Thread.sleep(10000);
                updateOrderStatus(orderId, OrderStatus.OUT_FOR_DELIVERY);
                
                // OUT_FOR_DELIVERY -> DELIVERED
                Thread.sleep(10000);
                updateOrderStatus(orderId, OrderStatus.DELIVERED);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        orderTableRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(newStatus);
            orderTableRepository.save(order);
        });
    }

    // =====================================================================
    // Entity - DTO Mapping
    // =====================================================================

    /**
     * Convert an {@link OrderTable} entity (with its items) to an {@link OrderResponseDTO}.
     */
    private OrderResponseDTO mapToDTO(OrderTable order) {
        List<OrderItemResponseDTO> itemDTOs = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getOrderItemId(),
                        item.getMenu().getMenuId(),
                        item.getMenu().getItemName(),
                        item.getQuantity(),
                        item.getMenu().getPrice(),
                        item.getItemTotal()
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getOrderId(),
                order.getUser().getUserId(),
                order.getRestaurant().getRestaurantId(),
                order.getRestaurant().getName(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getPaymentMethod().name(),
                itemDTOs
        );
    }
}
