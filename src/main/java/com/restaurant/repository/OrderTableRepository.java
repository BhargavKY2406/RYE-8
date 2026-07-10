package com.restaurant.repository;

import com.restaurant.entity.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link OrderTable} entity.
 * <p>
 * Provides CRUD operations plus custom finders for retrieving
 * a user's order history, ordered by most recent first.
 */
@Repository
public interface OrderTableRepository extends JpaRepository<OrderTable, Long> {

    /**
     * Find all orders placed by a specific user, sorted by date descending
     * so the most recent order appears first.
     *
     * @param userId the user's primary key
     * @return list of orders sorted newest-first
     */
    List<OrderTable> findByUser_UserIdOrderByOrderDateDesc(Long userId);
}
