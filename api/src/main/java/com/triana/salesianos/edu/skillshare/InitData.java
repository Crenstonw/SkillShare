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
                .email("hola@hola.com")
                .profilePicture("https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg")
                .name("user1")
                .surname("suruser1")
                .username("Antonioo23")
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode("a"))
                .userRole(EnumSet.of(UserRole.ADMIN))
                .build();
        User user2 = User.builder()
                .id(UUID.randomUUID())
                .email("adios@hola.com")
                .profilePicture("https://b2472105.smushcdn.com/2472105/wp-content/uploads/2023/01/Perfil-Profesional-_63-819x1024.jpg?lossy=1&strip=1&webp=1")
                .name("user2")
                .surname("suruser2")
                .username("SuMorenito23")
                .createdAt(LocalDateTime.now().minusYears(2))
                .password(passwordEncoder.encode("b"))
                .userRole(EnumSet.of(UserRole.USER))
                .build();
        User user3 = User.builder()
                .id(UUID.randomUUID())
                .email("aitormartinezmorzillo@gmail.com")
                .profilePicture("https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/e40b6ea6361a1abe28f32e7910f63b66/1-intro-photo-final.jpg?w=1200&h=992&fl=progressive&q=70&fm=jpg")
                .name("Aitor")
                .surname("Martinez Morcillo")
                .username("Athirot")
                .createdAt(LocalDateTime.now().minusYears(1))
                .password(passwordEncoder.encode("c"))
                .userRole(EnumSet.of(UserRole.USER))
                .build();
        userRepository.saveAll(List.of(user1, user2, user3));
        /////////////////////////////Direct Messages///////////////////////////////////////
        DirectMessage dm1 = DirectMessage.builder()
                .id(UUID.randomUUID())
                .title("cortacesped")
                .message("estoy interesado en tus servicios")
                .dateTime(LocalDateTime.now())
                .userFrom(user1)
                .userTo(user2)
                .build();
        DirectMessage dm2 = DirectMessage.builder()
                .id(UUID.randomUUID())
                .title("Sobre el cortacesped")
                .message("me he retirado lo siento :(")
                .dateTime(LocalDateTime.of(2021, 10, 12, 10, 22, 22))
                .userFrom(user2)
                .userTo(user1)
                .build();
        DirectMessage dm3 = DirectMessage.builder()
                .id(UUID.randomUUID())
                .title("Hola Aitor")
                .message("He visto que finalmente te has descargado la aplicación")
                .dateTime(LocalDateTime.now().minusDays(3L))
                .userFrom(user2)
                .userTo(user3)
                .build();
        DirectMessage dm4 = DirectMessage.builder()
                .id(UUID.randomUUID())
                .title("Que tal Rafael")
                .message("Siiii estaba viendo de impartir clases de inglés :)")
                .dateTime(LocalDateTime.now().minusDays(2L))
                .userFrom(user3)
                .userTo(user2)
                .build();
        DirectMessage dm5 = DirectMessage.builder()
                .id(UUID.randomUUID())
                .title("Guay")
                .message("Me alegro tio a ver si nos vemos algún día :D")
                .dateTime(LocalDateTime.now().minusDays(1L))
                .userFrom(user2)
                .userTo(user3)
                .build();
        directMessageRepository.saveAll(List.of(dm1, dm2, dm3, dm4, dm5));

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
                .title("Ensenio a comer caracoles")
                .user(user3)
                .state(OrderState.OPEN)
                .price(24.34)
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
                .price(25.34)
                .state(OrderState.OPEN)
                .description("descripcion 2 de la ordenanza")
                .tags(Set.of(tag1, tag2))
                .build();
        orderRepository.save(order2);

        Order order3 = Order.builder()
                .id(UUID.randomUUID())
                .title("Arreglo cortacespes")
                .user(user2)
                .price(26.34)
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
                .user(user2)
                .price(23.34)
                .state(OrderState.CLOSED)
                .description("descripcion 2 de la ordenanza")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order5);

        Order order6 = Order.builder()
                .id(UUID.randomUUID())
                .title("Paseo con perros")
                .user(user3)
                .price(20.50)
                .state(OrderState.OPEN)
                .description("Paseo con tus perros por el parque durante 1 hora.")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order6);

        Order order7 = Order.builder()
                .id(UUID.randomUUID())
                .title("Cuidado de plantas")
                .user(user2)
                .price(18.75)
                .state(OrderState.OCCUPIED)
                .description("Cuido de tus plantas durante tu ausencia.")
                .tags(Set.of(tag2))
                .build();
        orderRepository.save(order7);

        Order order8 = Order.builder()
                .id(UUID.randomUUID())
                .title("Limpieza del hogar")
                .user(user1)
                .price(30.00)
                .state(OrderState.OPEN)
                .description("Realizo limpieza completa del hogar.")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order8);

        Order order9 = Order.builder()
                .id(UUID.randomUUID())
                .title("Enseño guitarra")
                .user(user3)
                .price(40.00)
                .state(OrderState.OPEN)
                .description("Clases de guitarra para principiantes.")
                .tags(Set.of(tag2))
                .build();
        orderRepository.save(order9);

        Order order10 = Order.builder()
                .id(UUID.randomUUID())
                .title("Corte de cabello a domicilio")
                .user(user1)
                .price(15.00)
                .state(OrderState.CLOSED)
                .description("Servicio de corte de cabello profesional en tu hogar.")
                .tags(Set.of(tag2))
                .build();
        orderRepository.save(order10);

        Order order11 = Order.builder()
                .id(UUID.randomUUID())
                .title("Cambio de aceite para autos")
                .user(user2)
                .price(50.00)
                .state(OrderState.OCCUPIED)
                .description("Cambio de aceite y revisión básica para tu automóvil.")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order11);

        Order order12 = Order.builder()
                .id(UUID.randomUUID())
                .title("Reparación de grifos")
                .user(user3)
                .price(25.00)
                .state(OrderState.OPEN)
                .description("Reparo cualquier tipo de grifos en tu hogar.")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order12);

        Order order13 = Order.builder()
                .id(UUID.randomUUID())
                .title("Clases de cocina")
                .user(user2)
                .price(35.00)
                .state(OrderState.CLOSED)
                .description("Clases de cocina para aprender a preparar platos gourmet.")
                .tags(Set.of(tag2))
                .build();
        orderRepository.save(order13);

        Order order14 = Order.builder()
                .id(UUID.randomUUID())
                .title("Limpieza de jardines")
                .user(user1)
                .price(45.00)
                .state(OrderState.OCCUPIED)
                .description("Limpieza y mantenimiento de jardines y áreas verdes.")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order14);

        Order order15 = Order.builder()
                .id(UUID.randomUUID())
                .title("Servicio de lavandería")
                .user(user3)
                .price(20.00)
                .state(OrderState.CLOSED)
                .description("Lavado, secado y planchado de ropa.")
                .tags(Set.of(tag1))
                .build();
        orderRepository.save(order15);

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
        OrderMessage om3 = OrderMessage.builder()
                .id(UUID.randomUUID())
                .title("Me encantó el servicio")
                .message("muy buen servicio y fué un trato muy amable")
                .dateTime(LocalDateTime.of(2024, 4, 14, 12,34,54))
                .author(user2)
                .order(order3)
                .build();
        OrderMessage om4 = OrderMessage.builder()
                .id(UUID.randomUUID())
                .title("Sacaron lo mejor de mi")
                .message("Nunca supe lo bien que sentía una actividad como esta!")
                .dateTime(LocalDateTime.of(2024, 4, 14, 12,34,54))
                .author(user2)
                .order(order4)
                .build();
        orderMessageRepository.saveAll(List.of(om1, om2, om3, om4));
    }
}
