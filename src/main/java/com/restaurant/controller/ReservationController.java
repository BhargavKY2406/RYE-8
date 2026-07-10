package com.restaurant.controller;

import com.restaurant.dto.ApiResponseDTO;
import com.restaurant.dto.ReservationRequestDTO;
import com.restaurant.dto.ReservationResponseDTO;
import com.restaurant.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    private Long getAuthenticatedUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated. Please log in.");
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReservationResponseDTO>> createReservation(
            @RequestBody ReservationRequestDTO request,
            HttpSession session) {
        Long userId = getAuthenticatedUserId(session);
        ReservationResponseDTO dto = reservationService.createReservation(userId, request);
        return ResponseEntity.ok(ApiResponseDTO.success("Reservation created successfully", dto));
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<ApiResponseDTO<List<ReservationResponseDTO>>> getMyReservations(
            HttpSession session) {
        Long userId = getAuthenticatedUserId(session);
        List<ReservationResponseDTO> dtos = reservationService.getUserReservations(userId);
        return ResponseEntity.ok(ApiResponseDTO.success("Reservations retrieved", dtos));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponseDTO<ReservationResponseDTO>> cancelReservation(
            @PathVariable Long id,
            HttpSession session) {
        Long userId = getAuthenticatedUserId(session);
        ReservationResponseDTO dto = reservationService.cancelReservation(id, userId);
        return ResponseEntity.ok(ApiResponseDTO.success("Reservation cancelled", dto));
    }
}
