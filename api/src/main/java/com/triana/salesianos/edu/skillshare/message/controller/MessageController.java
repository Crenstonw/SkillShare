package com.triana.salesianos.edu.skillshare.message.controller;

import com.triana.salesianos.edu.skillshare.message.dto.*;
import com.triana.salesianos.edu.skillshare.message.service.MessageService;
import com.triana.salesianos.edu.skillshare.order.dto.NewOrderRequest;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
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

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/message")
public class MessageController {

    private final MessageService service;

    //////////////////////////////Direct Messages///////////////////////////////////
    @Operation(summary = "Get my direct messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain every message you posted",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/direct")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<DirectMessageResponse>> getMyDirectMessages(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getMyDirectMessages(pageable));
    }

    @Operation(summary = "GetMessagesById")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain the messages from a conversation between 2 users",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/direct/chat")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DirectMessageResponse>> getMessageById(@RequestParam String userFrom, @RequestParam String userTo, @RequestParam(required = false) boolean asc){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDirectMessageById(asc, userFrom, userTo));
    }

    @Operation(summary = "Get users who talked with")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain the users who talked at least once with the asked user",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("direct/users/{fromId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AllUserResponse>> getUserWhoTalkedWith(@PathVariable String fromId) {
        return ResponseEntity.ok().body(service.getUsersWhoTalkedWith(fromId));
    }

    @Operation(summary = "Post messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201",
                    description = "post a new direct message",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NewDirectMessageRequest.class))
                    )})
    })
    @PostMapping("/direct")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DirectMessageResponse> postMessage(@RequestBody NewDirectMessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postDirectMessage(request));
    }

    @Operation(summary = "Put message")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "put your direct message if user, put whatever message if admin",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NewDirectMessageRequest.class))
                    )})
    })
    @PutMapping("/direct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DirectMessageResponse> putMessage(
            @PathVariable String id,
            @RequestBody NewDirectMessageRequest request) {
        return null;
    }

    @Operation(summary = "Delete message")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="204",
                    description = "delete your direct message if user, delete whatever message if admin",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @DeleteMapping("/direct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> deleteMessage(@PathVariable String id) {
        service.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //////////////////////////////Order Messages///////////////////////////////////

    @Operation(summary = "Get order messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain every message of an order",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ListOrderMessageResponse> getOrderMessages(
            @PathVariable String orderId) {

        return ResponseEntity.status(HttpStatus.OK).body(service
                .getOrderMessages(orderId));
    }

    @Operation(summary = "Get order messages by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Obtain every message of an user",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/order/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<OrderMessageDetailResponse>> getOrderMessagesByUserId(
            @PageableDefault Pageable pageable,
            @PathVariable String userId) {
        return ResponseEntity.ok().body(service.getOrdersMessageByUser(pageable, userId));
    }

    @Operation(summary = "Post order message")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201",
                    description = "post a new message in an order",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NewOrderMessageRequest.class))
                    )})
    })
    @PostMapping("/order")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderMessageResponse> postOrderMessage(
            @RequestBody NewOrderMessageRequest messageRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.newOrderMessage(messageRequest));
    }

    @Operation(summary = "Put order message")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "put your order message if user, put whatever order message if admin",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NewOrderMessageRequest.class))
                    )})
    })
    @PutMapping("/order/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderMessageResponse> putOrderMessage(
            @PathVariable String id,
            @RequestBody EditOrderMessageRequest messageRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.editOrderMessage(id, messageRequest));
    }

    @Operation(summary = "Delete order message")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="204",
                    description = "delete your order message if user, delete whatever order message if admin",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @DeleteMapping("/order/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> deleteOrderMessage(@PathVariable String id) {
        service.deleteOrderMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
