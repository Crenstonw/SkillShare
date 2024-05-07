package com.triana.salesianos.edu.skillshare.order.service;

import com.triana.salesianos.edu.skillshare.Tag.service.TagService;
import com.triana.salesianos.edu.skillshare.order.dto.ListOrderResponse;
import com.triana.salesianos.edu.skillshare.order.dto.NewOrderRequest;
import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TagService tagService;

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderPage.map(OrderResponse::of);
    }

    public OrderResponse getOrderById(String id) {
        Order findOrder = orderRepository.findById(UUID.fromString(id))
                .orElseThrow(NoOrderException::new);

        return OrderResponse.of(findOrder);
    }

    public Page<OrderResponse> getOrderListByTitle(String title, Pageable pageable) {
        Page<Order> findOrders = orderRepository.findOrderListByTitle(title, pageable);
        return findOrders.map(OrderResponse::of);
    }

    public OrderResponse newOrder(NewOrderRequest orderRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NoOrderException::new);
        Order newOrder = Order.builder()
                .id(UUID.randomUUID())
                .title(orderRequest.title())
                .description(orderRequest.description())
                .tags(tagService.addTags(orderRequest.tags()))
                .user(user)
                .build();
        orderRepository.save(newOrder);
        return OrderResponse.of(newOrder);
    }

    public OrderResponse putOrder(String id, NewOrderRequest orderRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        Optional<Order> findOrder = orderRepository.findById(UUID.fromString(id));
        if(findOrder.isPresent() && Objects.equals(user, findOrder.get().getUser())) {
            Order response = Order.builder()
                    .id(findOrder.get().getId())
                    .title(orderRequest.title())
                    .description(orderRequest.description())
                    .state(findOrder.get().getState())
                    .createdAt(findOrder.get().getCreatedAt())
                    .lastTimeModified(LocalDateTime.now())
                    .tags(tagService.addTags(orderRequest.tags()))
                    .orderMessages(findOrder.get().getOrderMessages())
                    .user(findOrder.get().getUser())
                    .build();
            orderRepository.save(response);
            return OrderResponse.of(response);
        } else {
            return null; //throw error
        }

    }

    public void deleteOrder(String id) {
        Optional<Order> findOrder = orderRepository.findById(UUID.fromString(id));
        findOrder.ifPresent(orderRepository::delete);
    }

    public OrderResponse changeStatus (String id, String status) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        Optional<Order> findOrder = orderRepository.findById(UUID.fromString(id));
        if(findOrder.isPresent() && Objects.equals(user.get(), findOrder.get().getUser())) {
            Order result = Order.builder()
                    .id(findOrder.get().getId())
                    .tags(findOrder.get().getTags())
                    .orderMessages(findOrder.get().getOrderMessages())
                    .state(OrderState.valueOf(status))
                    .user(findOrder.get().getUser())
                    .lastTimeModified(LocalDateTime.now())
                    .createdAt(findOrder.get().getCreatedAt())
                    .description(findOrder.get().getDescription())
                    .title(findOrder.get().getTitle())
                    .build();
            orderRepository.save(result);
            return OrderResponse.of(result);
        } else {
            return null;
        }
    }
}
