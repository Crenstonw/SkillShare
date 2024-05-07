package com.triana.salesianos.edu.skillshare.Tag.repository;

import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {


    @Query("""
            SELECT t
            FROM Tag t
            WHERE t.name ILIKE ?1
            """)
    Optional<Tag> findTagByName(String name);
}
