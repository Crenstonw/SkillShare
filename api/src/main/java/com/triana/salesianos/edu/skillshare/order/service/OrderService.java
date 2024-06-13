package com.triana.salesianos.edu.skillshare.order.service;

import com.triana.salesianos.edu.skillshare.Tag.exceptions.TagNotFoundException;
import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.Tag.repository.TagRepository;
import com.triana.salesianos.edu.skillshare.Tag.service.TagService;
import com.triana.salesianos.edu.skillshare.order.dto.*;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.exception.NotEnoughPrivilegesException;
import com.triana.salesianos.edu.skillshare.user.exception.UserNotFound;
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
    private final TagRepository tagRepository;
    private final TagService tagService;

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Page<Order> orderPage;
        if(Objects.equals(user.getUserRole().toString(), "[ADMIN]"))
            orderPage = orderRepository.findAll(pageable);
        else {
            orderPage = orderRepository.findAllForUsers(pageable);
        }
        return orderPage.map(OrderResponse::of);
    }

    public Page<OrderResponse> getAllOrdersOrderByStatus(Pageable pageable, int status) {
        OrderState state = OrderState.values()[status];
        Page<Order> result = orderRepository.findByState(state, pageable);
        return result.map(OrderResponse::of);
    }

    public Page<OrderResponse> getAllOrdersOrderByPrice(Pageable pageable, boolean asc) {
        Page<Order> result = orderRepository.findOrderByPrice(asc, pageable);
        return result.map(OrderResponse::of);
    }

    public Page<OrderResponse> getAllOrdersOrderByTag(Pageable pageable, String tag) {
        Tag findTag = tagRepository.findTagByName(tag).orElseThrow(TagNotFoundException::new);
        Page<Order> result = orderRepository.findOrdersWithTag(findTag, pageable);
        return result.map(OrderResponse::of);
    }

    public OrderDetailsResponse getOrderById(String id) {
        Order findOrder = orderRepository.findById(UUID.fromString(id))
                .orElseThrow(NoOrderException::new);
        return OrderDetailsResponse.of(findOrder);
    }

    public Page<OrderResponse> getOrderListByTitle(String title, Pageable pageable) {
        Page<Order> findOrders = orderRepository.findOrderListByTitle(title, pageable);
        return findOrders.map(OrderResponse::of);
    }

    public Page<OrderResponse> getMyOrders(Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Page<Order> orderPage = orderRepository.findMyOrders(user, pageable);

        return orderPage.map(OrderResponse::of);
    }

    public OrderResponse newOrder(NewOrderRequest orderRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Order newOrder = Order.builder()
                .id(UUID.randomUUID())
                .title(orderRequest.title())
                .description(orderRequest.description())
                .price(orderRequest.price())
                .tags(tagService.addTags(orderRequest.tags()))
                .user(user)
                .build();
        orderRepository.save(newOrder);
        return OrderResponse.of(newOrder);
    }

    public OrderResponse putOrder(String id, NewOrderRequest orderRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Order findOrder = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);
        if(Objects.equals(user, findOrder.getUser())
            || Objects.equals(user.getUserRole().toString(), "[ADMIN]")) {
            Order response = Order.builder()
                    .id(findOrder.getId())
                    .title(orderRequest.title())
                    .price(orderRequest.price())
                    .description(orderRequest.description())
                    .state(findOrder.getState())
                    .createdAt(findOrder.getCreatedAt())
                    .lastTimeModified(LocalDateTime.now())
                    .tags(tagService.addTags(orderRequest.tags()))
                    .orderMessages(findOrder.getOrderMessages())
                    .user(findOrder.getUser())
                    .build();
            orderRepository.save(response);
            return OrderResponse.of(response);
        } else throw new NotEnoughPrivilegesException();

    }

    public OrderResponse changeStatus (String id, StatusDto status) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Order findOrder = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);
        if(Objects.equals(user, findOrder.getUser())
            || Objects.equals(user.getUserRole().toString(), "[ADMIN]")) {
            Order result = Order.builder()
                    .id(findOrder.getId())
                    .tags(findOrder.getTags())
                    .orderMessages(findOrder.getOrderMessages())
                    .price(findOrder.getPrice())
                    .state(OrderState.valueOf(status.status()))
                    .user(findOrder.getUser())
                    .lastTimeModified(LocalDateTime.now())
                    .createdAt(findOrder.getCreatedAt())
                    .description(findOrder.getDescription())
                    .title(findOrder.getTitle())
                    .build();
            orderRepository.save(result);
            return OrderResponse.of(result);
        } else throw new NotEnoughPrivilegesException();
    }

    public void deleteOrder(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Order findOrder = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);
        if(Objects.equals(user.getUsername(), findOrder.getUser().getUsername())
                || Objects.equals(user.getUserRole().toString(), "[ADMIN]")) {
            orderRepository.deleteFavoriteOrder(UUID.fromString(id));
            orderRepository.delete(findOrder);
        } else throw new NotEnoughPrivilegesException();
    }
}
