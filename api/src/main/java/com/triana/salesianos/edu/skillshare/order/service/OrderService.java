package com.triana.salesianos.edu.skillshare.order.service;

import com.triana.salesianos.edu.skillshare.order.dto.ListOrderResponse;
import com.triana.salesianos.edu.skillshare.order.dto.NewOrderRequest;
import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public ListOrderResponse getAllOrders() {
        List<Order> findAll = orderRepository.findAll();
        List<OrderResponse> list = new ArrayList<>();

        for(Order order : findAll) {list.add(OrderResponse.of(order));}
        ListOrderResponse result = ListOrderResponse.of(list);

        return result;
    }

    public OrderResponse getOrderById(String id) {
        Order findOrder = orderRepository.findById(UUID.fromString(id))
                .orElseThrow(NoOrderException::new);

        return OrderResponse.of(findOrder);
    }

    public ListOrderResponse getOrderListByTitle(String title) {
        List<Order> findOrders = orderRepository.findOrderListByTitle(title);
        List<OrderResponse> listToDto = new ArrayList<>();

        for(Order order : findOrders) {
            listToDto.add(OrderResponse.of(order));
        }
        return ListOrderResponse.of(listToDto);
    }

    public OrderResponse newOrder(NewOrderRequest orderRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NoOrderException::new);
        Order newOrder = Order.builder()
                .id(UUID.randomUUID())
                .title(orderRequest.title())
                .description(orderRequest.description())
                .user(user)
                .build();
        orderRepository.save(newOrder);
        return OrderResponse.of(newOrder);
    }

    public void deleteOrder(String id) {
        Optional<Order> findOrder = orderRepository.findById(UUID.fromString(id));
        if(findOrder.isPresent()) {
            orderRepository.delete(findOrder.get());
        }
    }
}
