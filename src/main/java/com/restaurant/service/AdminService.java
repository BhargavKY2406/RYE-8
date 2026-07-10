package com.restaurant.service;

import com.restaurant.dto.OrderItemResponseDTO;
import com.restaurant.dto.OrderResponseDTO;
import com.restaurant.dto.UserResponseDTO;
import com.restaurant.entity.AppUser;
import com.restaurant.entity.OrderStatus;
import com.restaurant.entity.OrderTable;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.AppUserRepository;
import com.restaurant.repository.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AppUserRepository userRepository;
    private final OrderTableRepository orderTableRepository;

    public AdminService(AppUserRepository userRepository, OrderTableRepository orderTableRepository) {
        this.userRepository = userRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapUserToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrders() {
        return orderTableRepository.findAll().stream()
                // Sort by ID descending so newest are first
                .sorted((o1, o2) -> o2.getOrderId().compareTo(o1.getOrderId()))
                .map(this::mapOrderToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        OrderTable order = orderTableRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        orderTableRepository.save(order);
        return mapOrderToDTO(order);
    }

    private UserResponseDTO mapUserToDTO(AppUser user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAddress(),
                user.getRole().name(),
                user.getCreatedDate(),
                user.getLastLoginDate()
        );
    }

    private OrderResponseDTO mapOrderToDTO(OrderTable order) {
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
                order.getDeliveryFee(),
                order.getAppFee(),
                order.getStatus().name(),
                order.getPaymentMethod().name(),
                itemDTOs
        );
    }
}
