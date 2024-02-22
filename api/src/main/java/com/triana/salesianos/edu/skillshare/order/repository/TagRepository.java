package com.triana.salesianos.edu.skillshare.order.repository;

import com.triana.salesianos.edu.skillshare.order.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
