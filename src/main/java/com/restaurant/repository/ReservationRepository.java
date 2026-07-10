package com.restaurant.repository;

import com.restaurant.entity.AppUser;
import com.restaurant.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserOrderByReservationDateDescReservationTimeDesc(AppUser user);
}
