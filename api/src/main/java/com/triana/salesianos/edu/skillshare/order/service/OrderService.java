package com.triana.salesianos.edu.skillshare.order.service;

import com.triana.salesianos.edu.skillshare.order.dto.ListOrderResponse;
import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.dto.Prueba;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

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
        List<Order> findOrders = orderRepository.findORderListByTitle(title);
        List<OrderResponse> listToDto = new ArrayList<>();

        for(Order order : findOrders) {
            listToDto.add(OrderResponse.of(order));
        }
        return ListOrderResponse.of(listToDto);
    }
}
