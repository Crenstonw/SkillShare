package com.triana.salesianos.edu.skillshare.message.service;

import com.triana.salesianos.edu.skillshare.message.dto.*;
import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.message.model.Message;
import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.message.repository.DirectMessageRepository;
import com.triana.salesianos.edu.skillshare.message.repository.OrderMessageRepository;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final DirectMessageRepository directMessageRepository;
    private final OrderMessageRepository orderMessageRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    //////////////////////////////Direct Messages///////////////////////////////////
    public ListDirectMessageResponse getMyDirectMessages() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        List<DirectMessage> findMessages = directMessageRepository.findDirectMessagesByUserFrom(user.get());
        List<DirectMessageResponse> result = new ArrayList<>();
        for(DirectMessage dm : findMessages) {
            result.add(DirectMessageResponse.of(dm));
        }
        return ListDirectMessageResponse.of(result);
    }

    public DirectMessageResponse getDirectMessageById(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<DirectMessage> findMessage = directMessageRepository.findById(UUID.fromString(id));
        if (findMessage.isPresent()) {
            if(Objects.equals(findMessage.get().getUserFrom().getUsername(), userDetails.getUsername())) {
                return DirectMessageResponse.of(findMessage.get());
            } else {
                return null; //throw error
            }
        } else {
            return null; //throw error
        }
    }

    public DirectMessageResponse postDirectMessage(NewDirectMessageRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userFrom = userRepository.findByUsername(userDetails.getUsername());
        Optional<User> userTo = userRepository.findByEmail(request.toUserEmail());
        if(userTo.isPresent()) {
            DirectMessage directMessage = DirectMessage.builder()
                    .title(request.title())
                    .message(request.message())
                    .dateTime(LocalDateTime.now())
                    .userFrom(userFrom.get())
                    .userTo(userTo.get())
                    .build();
            directMessageRepository.save(directMessage);
            return DirectMessageResponse.of(directMessage);
        } else {
            return null; //throw error
        }
    }

    public void deleteMessage(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userFrom = userRepository.findByUsername(userDetails.getUsername());
        Optional<DirectMessage> findMessage = directMessageRepository.findById(UUID.fromString(id));
        if(findMessage.isPresent() && Objects.equals(findMessage.get().getUserFrom(), userFrom.get())) {
            directMessageRepository.delete(findMessage.get());
        } else {
            //throw error
        }
    }
    //////////////////////////////Order Messages///////////////////////////////////
    public ListOrderMessageResponse getOrderMessages(String id) {
        Optional<Order> findOrder = orderRepository.findById(UUID.fromString(id));
        if(findOrder.isPresent()) {
            List<OrderMessage> getMessages = orderMessageRepository
                    .getMessagesOfAnOrder(findOrder.get());
            List<OrderMessageResponse> response = new ArrayList<>();
            for(OrderMessage om : getMessages) {
                response.add(OrderMessageResponse.of(om));
            }
            return ListOrderMessageResponse.of(response);
        } else {
            return null; //throw error
        }
    }

    public OrderMessageResponse newOrderMessage(NewOrderMessageRequest messageRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        Optional<Order> findOrder = orderRepository.findById(UUID.fromString(messageRequest.orderId()));
        if(findOrder.isPresent()) {
            OrderMessage result = OrderMessage.builder()
                    .title(messageRequest.title())
                    .message(messageRequest.message())
                    .dateTime(LocalDateTime.now())
                    .order(findOrder.get())
                    .author(user.get())
                    .build();
            orderMessageRepository.save(result);
            return OrderMessageResponse.of(result);
        } else {
            return null; //throw error
        }

    }

    public OrderMessageResponse editOrderMessage(String id, EditOrderMessageRequest messageRequest) {
        Optional<OrderMessage> findMessage = orderMessageRepository
                .findById(UUID.fromString(id));
        if(findMessage.isPresent()) {
            OrderMessage result = OrderMessage.builder()
                    .id(findMessage.get().getId())
                    .title(messageRequest.title())
                    .message(messageRequest.message())
                    .order(findMessage.get().getOrder())
                    .dateTime(findMessage.get().getDateTime())
                    .author(findMessage.get().getAuthor())
                    .build();
            orderMessageRepository.save(result);
            return OrderMessageResponse.of(result);
        } else {
            return null; //throw error
        }
    }

    public void deleteOrderMessage(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        Optional<OrderMessage> findMessage = orderMessageRepository
                .findById(UUID.fromString(id));
        if(findMessage.isPresent() && findMessage.get().getAuthor() == user.get() || Objects.equals(user.get().getUserRole().toString(), "[ADMIN]")) {
            orderMessageRepository.delete(findMessage.get());
        } else {
             //throw error
        }
    }
}
