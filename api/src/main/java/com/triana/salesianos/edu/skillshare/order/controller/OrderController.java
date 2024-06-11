package com.triana.salesianos.edu.skillshare.order.controller;

import com.triana.salesianos.edu.skillshare.order.dto.*;
import com.triana.salesianos.edu.skillshare.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderDetailsResponse> getOrderById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderById(id));
    }

    @GetMapping("/search/{title}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<OrderResponse>> getOrderListByTitle(@PathVariable String title, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderListByTitle(title, pageable));
    }

    @GetMapping("/myOrders")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<OrderResponse>> getMyOrderList(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(service.getMyOrders(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> newOrder(@RequestBody NewOrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newOrder(order));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> putOrder(
            @PathVariable String id,
            @RequestBody NewOrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.putOrder(id, orderRequest));
    }

    @PutMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> changeStatus(@PathVariable String id, @RequestBody StatusDto status) {
        return ResponseEntity.status(HttpStatus.OK).body(service.changeStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        service.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
