package com.triana.salesianos.edu.skillshare.message.repository;

import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<DirectMessage> findDirectMessagesByUserFrom(User user, Pageable pageable);

    @Query("""
            SELECT dm
            FROM DirectMessage dm
            WHERE dm.dateTime >= ?1
            """)
    List<DirectMessage> findDirectMessagesExpired(LocalDateTime expiredDate);

    @Query("""
            SELECT dm
            FROM DirectMessage dm
            WHERE dm.userFrom = ?1
            OR dm.userFrom = ?2
            AND dm.userTo = ?2
            OR dm.userTo = ?1
            ORDER BY dm.dateTime DESC
            """)
    List<DirectMessage> findDirectMessagesByUserFromUser(User from, User to);

    @Query("""
           SELECT DISTINCT u
           FROM DirectMessage dm
           JOIN User u ON dm.userTo = u
           WHERE dm.userFrom = ?1
           OR dm.userTo = ?1
           """)
    List<User> findUniqueUsersMessagedBy(User from);
}
