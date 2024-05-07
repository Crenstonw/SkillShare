package com.triana.salesianos.edu.skillshare.message.repository;

import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, UUID> {
    @Query("""
            SELECT dm
            FROM DirectMessage dm
            WHERE dm.userFrom = ?1
            AND dm.userTo = ?1
            """)
    List<DirectMessage> findDirectMessagesByUserFrom(User user);

    @Query("""
            SELECT dm
            FROM DirectMessage dm
            WHERE dm.dateTime >= ?1
            """)
    List<DirectMessage> findDirectMessagesExpired(LocalDateTime expiredDate);
}
