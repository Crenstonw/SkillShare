package com.triana.salesianos.edu.skillshare.message.repository;

import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, UUID> {
}
