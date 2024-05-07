package com.triana.salesianos.edu.skillshare.order.repository;

import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findAll(Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.title LIKE %:title%
            """)
    Page<Order> findOrderListByTitle(String title, Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.lastTimeModified >= ?1
            AND o.state = ?2
            """)
    List<Order> findDeleteableOrders(LocalDateTime expiredDate, OrderState state);

    @Query("""
            SELECT DISTINCT o
            FROM Order o JOIN o.tags t
            WHERE  t IN ?1
            """)
    List<Order> findOrdersByDate();
}
