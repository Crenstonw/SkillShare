package com.triana.salesianos.edu.skillshare.order.model;

import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "order_entity")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
public class Order {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Enumerated
    @Column(name = "order_state", nullable = false)
    @Builder.Default
    private OrderState state = OrderState.OPEN;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_time_modified")
    @Builder.Default
    private LocalDateTime lastTimeModified = LocalDateTime.now();

    @ManyToMany
    @JoinTable(name = "order_entity_tags",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private Set<OrderMessage> orderMessages = new LinkedHashSet<>();

}
