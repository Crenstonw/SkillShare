package com.triana.salesianos.edu.skillshare.message.controller;

import com.triana.salesianos.edu.skillshare.message.dto.DirectMessageResponse;
import com.triana.salesianos.edu.skillshare.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService service;
    @GetMapping("/direct/{id}")
    public ResponseEntity<DirectMessageResponse> getMessageById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDirectMessageById(id));
    }
}
