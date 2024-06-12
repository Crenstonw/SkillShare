package com.triana.salesianos.edu.skillshare.order.repository;

import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
            SELECT o
            FROM Order o
            ORDER BY o.createdAt DESC
            """)
    Page<Order> findAll(Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.state = ?1
            ORDER BY o.createdAt DESC
            """)
    Page<Order> findByState(OrderState state, Pageable pageable);


    @Query("""
            SELECT o
            FROM Order o
            ORDER BY
                CASE WHEN ?1 = true THEN o.price END ASC,
                CASE WHEN ?1 = false THEN o.price END DESC,
            o.createdAt DESC
            """)
    Page<Order> findOrderByPrice(boolean asc, Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE ?1 MEMBER OF o.tags
            ORDER BY o.createdAt DESC
            """)
    Page<Order> findOrdersWithTag(Tag tag, Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.state <> 2
            ORDER BY o.createdAt DESC
            """)
    Page<Order> findAllForUsers(Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.title LIKE %:title%
            """)
    Page<Order> findOrderListByTitle(String title, Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.user = ?1
            """)
    Page<Order> findMyOrders(User user, Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.lastTimeModified >= ?1
            AND o.state = ?2
            """)
    List<Order> findDeleteableOrders(LocalDateTime expiredDate, OrderState state);
}
