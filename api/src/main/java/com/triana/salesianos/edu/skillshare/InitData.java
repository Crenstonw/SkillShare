package com.triana.salesianos.edu.skillshare;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.Tag;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.order.repository.TagRepository;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.model.UserRole;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final TagRepository tagRepository;

    @PostConstruct
    public void initData() {
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .email("user1@email.com")
                .profilePicture("https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg")
                .name("user1")
                .surname("suruser1")
                .username("username")
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode("1234"))
                .userRole(EnumSet.of(UserRole.ADMIN))
                .build();
        userRepository.save(user1);

        /*Tag tag1 = Tag.builder()
                .id(UUID.randomUUID())
                .name("tag1")
                .build();
        Tag tag2 = Tag.builder()
                .id(UUID.randomUUID())
                .name("tag2")
                .build();
        tagRepository.save(tag1);*/

        Order order1 = Order.builder()
                .id(UUID.fromString("e438c08c-4e3b-48dc-9b35-95e5ddbdff81"))
                .title("titulo")
                .user(user1)
                .description("descripcion de la ordenanza")
                //.tags(Set.of(tag1))
                .build();
        orderRepository.save(order1);

        Order order2 = Order.builder()
                .id(UUID.randomUUID())
                .title("titulo2")
                .user(user1)
                .description("descripcion 2 de la ordenanza")
                //.tags(Set.of(tag1))
                .build();
        orderRepository.save(order2);
    }
}
