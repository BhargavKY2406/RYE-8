package com.restaurant.service;

import com.restaurant.dto.ReservationRequestDTO;
import com.restaurant.dto.ReservationResponseDTO;
import com.restaurant.entity.AppUser;
import com.restaurant.entity.Reservation;
import com.restaurant.entity.Restaurant;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.AppUserRepository;
import com.restaurant.repository.ReservationRepository;
import com.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public ReservationResponseDTO createReservation(Long userId, ReservationRequestDTO request) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + request.getRestaurantId()));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        reservation.setReservationDate(request.getReservationDate());
        reservation.setReservationTime(request.getReservationTime());
        reservation.setPartySize(request.getPartySize());
        reservation.setSpecialRequests(request.getSpecialRequests());

        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToDTO(savedReservation);
    }

    public List<ReservationResponseDTO> getUserReservations(Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        List<Reservation> reservations = reservationRepository.findByUserOrderByReservationDateDescReservationTimeDesc(user);
        return reservations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponseDTO cancelReservation(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + reservationId));
        
        if (!reservation.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Not authorized to cancel this reservation");
        }

        reservation.setStatus("CANCELLED");
        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapToDTO(updatedReservation);
    }

    private ReservationResponseDTO mapToDTO(Reservation res) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(res.getId());
        dto.setReservationDate(res.getReservationDate());
        dto.setReservationTime(res.getReservationTime());
        dto.setPartySize(res.getPartySize());
        dto.setSpecialRequests(res.getSpecialRequests());
        dto.setStatus(res.getStatus());
        dto.setCreatedAt(res.getCreatedAt());
        dto.setRestaurantId(res.getRestaurant().getRestaurantId());
        dto.setRestaurantName(res.getRestaurant().getName());
        dto.setRestaurantAddress(res.getRestaurant().getAddress());
        return dto;
    }
}
