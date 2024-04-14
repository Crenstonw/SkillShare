package com.triana.salesianos.edu.skillshare.message.controller;

import com.triana.salesianos.edu.skillshare.message.dto.DirectMessageResponse;
import com.triana.salesianos.edu.skillshare.message.dto.ListDirectMessageResponse;
import com.triana.salesianos.edu.skillshare.message.dto.NewDirectMessageRequest;
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
    public ResponseEntity<DirectMessageResponse> putMessage(@PathVariable String id, @RequestBody NewDirectMessageRequest request) {
        return null;
    }

    @DeleteMapping("/direct/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable String id) {
        service.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //////////////////////////////Order Messages///////////////////////////////////
}
