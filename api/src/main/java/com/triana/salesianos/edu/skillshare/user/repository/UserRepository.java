package com.triana.salesianos.edu.skillshare.user.repository;

import com.triana.salesianos.edu.skillshare.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
            SELECT u FROM User u WHERE u.email = ?1
            """)
    Optional<User> buscarPorEmail(String email);
}