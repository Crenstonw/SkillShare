package com.triana.salesianos.edu.skillshare.message.repository;

import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderMessageRepository extends JpaRepository<OrderMessage, UUID> {
}
