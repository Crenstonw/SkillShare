package com.triana.salesianos.edu.skillshare.message.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
//@Table(name = "message_entity")
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

}
