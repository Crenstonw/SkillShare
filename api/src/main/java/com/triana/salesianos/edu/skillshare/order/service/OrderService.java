package com.triana.salesianos.edu.skillshare.order.service;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderResponse> getAllOrders() {
        List<Order> findAll = orderRepository.findAll();
        List<OrderResponse> result = new ArrayList<>();

        for(Order order : findAll) {
            result.add(OrderResponse.of(order));
        }

        return result;
    }
    
}
