package com.restaurant.repository;

import com.restaurant.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link OrderItem} entity.
 * <p>
 * Provides CRUD operations plus a finder to retrieve all line-items
 * belonging to a specific order.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Find all line-items for a given order.
     *
     * @param orderId the order's primary key
     * @return list of order items
     */
    List<OrderItem> findByOrder_OrderId(Long orderId);
}
