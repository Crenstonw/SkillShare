package com.triana.salesianos.edu.skillshare.order.controller;

import com.triana.salesianos.edu.skillshare.Tag.dto.TagRequest;
import com.triana.salesianos.edu.skillshare.order.dto.*;
import com.triana.salesianos.edu.skillshare.order.exception.TooManyParametersException;
import com.triana.salesianos.edu.skillshare.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain every order with the hability to sort by status, price or tag",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Optional<Integer> status,
            @RequestParam(required = false) Optional<Boolean> price,
            @RequestParam(required = false) Optional<String> tag) {
        if(status.isEmpty() && price.isEmpty() && tag.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrders(pageable));
        else if(status.isPresent() && price.isEmpty() && tag.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.getAllOrdersOrderByStatus(pageable, status.get()));
        else if(status.isEmpty() && price.isPresent() && tag.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.getAllOrdersOrderByPrice(pageable, price.get()));
        else if(status.isEmpty() && price.isEmpty() && tag.isPresent())
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.getAllOrdersOrderByTag(pageable, tag.get()));
        else
            throw new TooManyParametersException();
    }

    @Operation(summary = "Get order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain an order by it's id",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderDetailsResponse> getOrderById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderById(id));
    }

    @Operation(summary = "Get order by title")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain orders but you can search my it's title",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/search/{title}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<OrderResponse>> getOrderListByTitle(@PathVariable String title, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderListByTitle(title, pageable));
    }

    @Operation(summary = "Get my order list")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain all your posted orders",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/myOrders")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<OrderResponse>> getMyOrderList(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(service.getMyOrders(pageable));
    }

    @Operation(summary = "New order")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201",
                    description = "Post a new order",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NewOrderRequest.class))
                    )})
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> newOrder(@RequestBody NewOrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newOrder(order));
    }

    @Operation(summary = "Put order")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Modify an order",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NewOrderRequest.class))
                    )})
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> putOrder(
            @PathVariable String id,
            @RequestBody NewOrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.putOrder(id, orderRequest));
    }

    @Operation(summary = "Change status")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Modify an order's status",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StatusDto.class))
                    )})
    })
    @PutMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> changeStatus(@PathVariable String id, @RequestBody StatusDto status) {
        return ResponseEntity.status(HttpStatus.OK).body(service.changeStatus(id, status));
    }

    @Operation(summary = "Delete order")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="204",
                    description = "Delete an existing order",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        service.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
