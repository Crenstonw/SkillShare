package com.triana.salesianos.edu.skillshare.order.controller;

import com.triana.salesianos.edu.skillshare.order.dto.ListOrderResponse;
import com.triana.salesianos.edu.skillshare.order.dto.NewOrderRequest;
import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<ListOrderResponse> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderById(id));
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<ListOrderResponse> getOrderListByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderListByTitle(title));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> newOrder(@RequestBody NewOrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> putOrder(
            @PathVariable String id,
            @RequestBody NewOrderRequest orderRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        service.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
