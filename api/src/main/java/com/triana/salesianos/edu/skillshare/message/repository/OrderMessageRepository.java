package com.triana.salesianos.edu.skillshare.message.repository;

import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderMessageRepository extends JpaRepository<OrderMessage, UUID> {
    @Query("""
        SELECT om
        FROM OrderMessage om
        WHERE om.order = ?1
        """)
    List<OrderMessage> getMessagesOfAnOrder(Order order);

    @Query("""
            SELECT om
            FROM OrderMessage om
            WHERE om.author = ?1
            ORDER BY om.dateTime ASC
            """)
    Page<OrderMessage> getMessagesOfAnUser(Pageable pageable, User user);
}
