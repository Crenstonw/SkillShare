package com.triana.salesianos.edu.skillshare.message.controller;

import com.triana.salesianos.edu.skillshare.message.dto.*;
import com.triana.salesianos.edu.skillshare.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService service;

    //////////////////////////////Direct Messages///////////////////////////////////
    @GetMapping("/direct")
    public ResponseEntity<ListDirectMessageResponse> getMyDirectMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getMyDirectMessages());
    }
    @GetMapping("/direct/{id}")
    public ResponseEntity<DirectMessageResponse> getMessageById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDirectMessageById(id));
    }

    @PostMapping("/direct")
    public ResponseEntity<DirectMessageResponse> postMessage(@RequestBody NewDirectMessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postDirectMessage(request));
    }

    @PutMapping("/direct/{id}")
    public ResponseEntity<DirectMessageResponse> putMessage(
            @PathVariable String id,
            @RequestBody NewDirectMessageRequest request) {
        return null;
    }

    @DeleteMapping("/direct/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable String id) {
        service.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //////////////////////////////Order Messages///////////////////////////////////

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ListOrderMessageResponse> getOrderMessages(
            @PathVariable String orderId) {

        return ResponseEntity.status(HttpStatus.OK).body(service
                .getOrderMessages(orderId));
    }

    @PostMapping("/order")
    public ResponseEntity<OrderMessageResponse> postOrderMessage(
            @RequestBody NewOrderMessageRequest messageRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.newOrderMessage(messageRequest));
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<OrderMessageResponse> putOrderMessage(
            @PathVariable String id,
            @RequestBody EditOrderMessageRequest messageRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.editOrderMessage(id, messageRequest));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrderMessage(@PathVariable String id) {
        service.deleteOrderMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
