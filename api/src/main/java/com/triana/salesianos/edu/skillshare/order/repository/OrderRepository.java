package com.triana.salesianos.edu.skillshare.order.repository;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
