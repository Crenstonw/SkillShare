package com.triana.salesianos.edu.skillshare.order.controller;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDeleter {
    private final OrderRepository repository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOrders() {
        LocalDateTime expireDate = LocalDateTime.now().minusMonths(2);
        OrderState state = OrderState.CLOSED;
        List<Order> allExpiredOrders = repository.findDeleteableOrders(expireDate, state);
        repository.deleteAll(allExpiredOrders);
    }
}
