package com.triana.salesianos.edu.skillshare.user.repository;

import com.triana.salesianos.edu.skillshare.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
            SELECT u
            FROM User u
            ORDER BY u.username DESC
            """)
    Page<User> findAllUsers(Pageable pageable);

    @Query("""
            SELECT u FROM User u WHERE u.email = ?1
            """)
    Optional<User> findByEmail(String email);

    @Query("""
            SELECT u FROM User u WHERE u.username = ?1
            """)
    Optional<User> findByUsername(String username);
}
