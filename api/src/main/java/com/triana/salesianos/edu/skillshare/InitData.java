package com.triana.salesianos.edu.skillshare;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.model.UserRole;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .email("user1@email.com")
                .name("user1")
                .surname("suruser1")
                .username("username")
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode("1234"))
                .userRole(EnumSet.of(UserRole.ADMIN))
                .build();
        userRepository.save(user1);

        Order order1 = Order.builder()
                .id(UUID.randomUUID())
                .title("titulo")
                .user(user1)
                .description("descripcion de la ordenanza")
                .build();
        orderRepository.save(order1);
    }
}
