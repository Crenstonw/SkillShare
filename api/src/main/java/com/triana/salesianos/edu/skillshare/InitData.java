package com.triana.salesianos.edu.skillshare;

import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.message.repository.DirectMessageRepository;
import com.triana.salesianos.edu.skillshare.message.repository.OrderMessageRepository;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.Tag.repository.TagRepository;
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
    private final DirectMessageRepository directMessageRepository;
    private final OrderMessageRepository orderMessageRepository;

    @PostConstruct
    public void initData() {
        /////////////////////////////Users///////////////////////////////////////
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .email("a")
                .profilePicture("https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg")
                .name("user1")
                .surname("suruser1")
                .username("ManoloOlo23")
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode("a"))
                .userRole(EnumSet.of(UserRole.ADMIN))
                .build();
        User user2 = User.builder()
                .id(UUID.randomUUID())
                .email("b")
                .profilePicture("https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg")
                .name("user2")
                .surname("suruser2")
                .username("MiAbeja39")
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode("b"))
                .userRole(EnumSet.of(UserRole.USER))
                .build();
        userRepository.saveAll(List.of(user1, user2));
        /////////////////////////////Direct Messages///////////////////////////////////////
        DirectMessage dm1 = DirectMessage.builder()
                .id(UUID.randomUUID())
                .title("titulo del mensaje directo")
                .message("mensaje del mensaje directo")
                .dateTime(LocalDateTime.now())
                .userFrom(user1)
                .userTo(user2)
                .build();
        DirectMessage dm2 = DirectMessage.builder()
                .id(UUID.randomUUID())
                .title("titulo del mensaje directo antiguo")
                .message("mensaje del mensaje directo antiguo")
                .dateTime(LocalDateTime.of(2021, 10, 12, 10, 22, 22))
                .userFrom(user1)
                .userTo(user2)
                .build();
        directMessageRepository.saveAll(List.of(dm1, dm2));

        /////////////////////////////Tags///////////////////////////////////////
        Tag tag1 = Tag.builder()
                .id(UUID.randomUUID())
                .name("tag1")
                .build();
        Tag tag2 = Tag.builder()
                .id(UUID.randomUUID())
                .name("tag2")
                .build();
        tagRepository.saveAll(List.of(tag1, tag2));

        /////////////////////////////Orders///////////////////////////////////////
        Order order1 = Order.builder()
                .id(UUID.fromString("e438c08c-4e3b-48dc-9b35-95e5ddbdff81"))
                .title("titulo")
                .user(user1)
                .state(OrderState.OPEN)
                .price(23.34)
                .description("descripcion de la ordenanza lorem ipsum amet")
                .createdAt(LocalDateTime.now().minusMonths(4))
                .lastTimeModified(LocalDateTime.now().minusMonths(1).minusDays(29))
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order1);

        Order order2 = Order.builder()
                .id(UUID.randomUUID())
                .title("titulo2")
                .user(user1)
                .price(23.34)
                .state(OrderState.OPEN)
                .description("descripcion 2 de la ordenanza")
                .tags(Set.of(tag1, tag2))
                .build();
        orderRepository.save(order2);

        Order order3 = Order.builder()
                .id(UUID.randomUUID())
                .title("Arreglo cortacespes")
                .user(user1)
                .price(23.34)
                .state(OrderState.OCCUPIED)
                .description("Hago de todo en realidad, pero arreglar cortacespes es lo que mejor se me da")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order3);

        Order order4 = Order.builder()
                .id(UUID.randomUUID())
                .title("Saco la basura por ti")
                .user(user1)
                .price(23.34)
                .state(OrderState.CLOSED)
                .description("descripcion 2 de la ordenanza")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order4);

        Order order5 = Order.builder()
                .id(UUID.randomUUID())
                .title("titulo3")
                .user(user1)
                .price(23.34)
                .state(OrderState.CLOSED)
                .description("descripcion 2 de la ordenanza")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order5);

        /////////////////////////////Order Messages///////////////////////////////////////
        OrderMessage om1 = OrderMessage.builder()
                .id(UUID.randomUUID())
                .title("muy bueno")
                .message("muy buen servicio")
                .dateTime(LocalDateTime.of(2024, 4, 14, 12,34,54))
                .author(user2)
                .order(order1)
                .build();
        OrderMessage om2 = OrderMessage.builder()
                .id(UUID.randomUUID())
                .title("Gracias a todos")
                .message("gracias a todos por vuestro apoyo")
                .dateTime(LocalDateTime.of(2024, 4, 14, 12,34,54))
                .author(user1)
                .order(order1)
                .build();
        orderMessageRepository.saveAll(List.of(om1, om2));
    }
}
