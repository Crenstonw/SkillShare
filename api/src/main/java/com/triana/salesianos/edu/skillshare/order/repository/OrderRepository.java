package com.triana.salesianos.edu.skillshare.order.repository;

import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
            SELECT o
            FROM Order o
            WHERE o.title ILIKE %:title%
            """)
    List<Order> findOrderListByTitle(String title);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.lastTimeModified >= ?1
            AND o.state = ?2
            """)
    List<Order> findDeleteableOrders(LocalDateTime exiredDate, OrderState state);
}
