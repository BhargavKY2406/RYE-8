package com.restaurant.controller;

import com.restaurant.dto.ApiResponseDTO;
import com.restaurant.dto.OrderResponseDTO;
import com.restaurant.dto.UserResponseDTO;
import com.restaurant.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "ADMIN".equalsIgnoreCase(role);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getAllUsers(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseDTO.error("Access denied. Admin role required."));
        }
        
        List<UserResponseDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(ApiResponseDTO.success("Users retrieved", users));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponseDTO<List<OrderResponseDTO>>> getAllOrders(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseDTO.error("Access denied. Admin role required."));
        }

        List<OrderResponseDTO> orders = adminService.getAllOrders();
        return ResponseEntity.ok(ApiResponseDTO.success("Orders retrieved", orders));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            HttpSession session) {
        
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseDTO.error("Access denied. Admin role required."));
        }

        OrderResponseDTO updatedOrder = adminService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(ApiResponseDTO.success("Order status updated", updatedOrder));
    }
}
