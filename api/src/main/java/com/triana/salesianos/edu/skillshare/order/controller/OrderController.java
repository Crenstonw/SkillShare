package com.triana.salesianos.edu.skillshare.order.controller;

import com.triana.salesianos.edu.skillshare.order.dto.ListOrderResponse;
import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.dto.Prueba;
import com.triana.salesianos.edu.skillshare.order.service.OrderService;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/order")
    public ResponseEntity<ListOrderResponse> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrders());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderById(id));
    }

    @GetMapping("/order/find/{title}")
    public ResponseEntity<ListOrderResponse> getOrderListByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderListByTitle(title));
    }

}
