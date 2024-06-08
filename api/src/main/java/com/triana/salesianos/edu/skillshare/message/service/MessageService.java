package com.triana.salesianos.edu.skillshare.message.service;

import com.triana.salesianos.edu.skillshare.message.dto.*;
import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.message.repository.DirectMessageRepository;
import com.triana.salesianos.edu.skillshare.message.repository.OrderMessageRepository;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import com.triana.salesianos.edu.skillshare.user.exception.UserNotFound;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Page<DirectMessageResponse> getMyDirectMessages(Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Page<DirectMessage> directMessagePage = directMessageRepository.findDirectMessagesByUserFrom(user, pageable);
        return directMessagePage.map(DirectMessageResponse::of);
    }

    public List<AllUserResponse> getUsersWhoTalkedWith(String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFound::new);
        List<User> findUsers = directMessageRepository.findUniqueUsersMessagedBy(user);
        findUsers.removeIf(u -> Objects.equals(u, user));
        List<AllUserResponse> result = new ArrayList<>();
        for(User u : findUsers) {
            result.add(AllUserResponse.of(u));
        }
        return result;
    }

    public List<DirectMessageResponse> getDirectMessageById(Boolean asc, String userFromId, String userToId) {
        User userFrom = userRepository.findById(UUID.fromString(userFromId)).orElseThrow(UserNotFound::new);
        User userTo = userRepository.findById(UUID.fromString(userToId)).orElseThrow(UserNotFound::new);
        List<DirectMessage> messages = directMessageRepository.findDirectMessagesByUserFromUser(userFrom, userTo);
        List<DirectMessageResponse> response = new ArrayList<>();
        for(DirectMessage message : messages) {
            response.add(DirectMessageResponse.of(message));
        }
        if(asc) {
            Collections.reverse(response);
        }
        return response;
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
        if(findMessage.isPresent()
                && Objects.equals(findMessage.get().getUserFrom(), userFrom.get())
                || Objects.equals(userFrom.get().getUserRole().toString(), "[ADMIN]")) {
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

    public Page<OrderMessageDetailResponse> getOrdersMessageByUser(Pageable pageable, String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFound::new);
        Page<OrderMessage> messages = orderMessageRepository.getMessagesOfAnUser(pageable, user);
        return messages.map(OrderMessageDetailResponse::of);
    }

    public OrderMessageResponse newOrderMessage(NewOrderMessageRequest messageRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Order findOrder = orderRepository.findById(UUID.fromString(messageRequest.orderId())).orElseThrow(NoOrderException::new);
            OrderMessage result = OrderMessage.builder()
                    .id(UUID.randomUUID())
                    .title(messageRequest.title())
                    .message(messageRequest.message())
                    .dateTime(LocalDateTime.now())
                    .order(findOrder)
                    .author(user)
                    .build();
            orderMessageRepository.save(result);
            return OrderMessageResponse.of(result);

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
