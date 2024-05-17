package com.triana.salesianos.edu.skillshare.Tag.repository;

import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("""
            SELECT t
            FROM Tag t
            ORDER BY t.name ASC
            """)
    Page<Tag> finAllTags(Pageable pageable);

    @Query("""
            SELECT t
            FROM Tag t
            WHERE t.name ILIKE ?1
            """)
    Optional<Tag> findTagByName(String name);

    @Transactional
    @Modifying
    @Query(value = """
        DELETE FROM order_entity_tags WHERE tags_id = ?1
        """, nativeQuery = true)
    void deleteOrderTags(UUID id);
}
