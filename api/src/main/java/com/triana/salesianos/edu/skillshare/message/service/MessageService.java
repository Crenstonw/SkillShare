package com.triana.salesianos.edu.skillshare.message.service;

import com.triana.salesianos.edu.skillshare.message.dto.*;
import com.triana.salesianos.edu.skillshare.message.exception.MessageNotFoundException;
import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.message.repository.DirectMessageRepository;
import com.triana.salesianos.edu.skillshare.message.repository.OrderMessageRepository;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import com.triana.salesianos.edu.skillshare.user.exception.NotEnoughPrivilegesException;
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
        User userFrom = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        User userTo = userRepository.findByEmail(request.toUserEmail()).orElseThrow(UserNotFound::new);
        DirectMessage directMessage = DirectMessage.builder()
                .title(request.title())
                .message(request.message())
                .dateTime(LocalDateTime.now())
                .userFrom(userFrom)
                .userTo(userTo)
                .build();
        directMessageRepository.save(directMessage);
        return DirectMessageResponse.of(directMessage);
    }

    public void deleteMessage(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFrom = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        DirectMessage findMessage = directMessageRepository
                .findById(UUID.fromString(id))
                .orElseThrow(MessageNotFoundException::new);
        if(Objects.equals(findMessage.getUserFrom(), userFrom)
                || Objects.equals(userFrom.getUserRole().toString(), "[ADMIN]")) {
            directMessageRepository.delete(findMessage);
        } else throw new NotEnoughPrivilegesException();
    }
    //////////////////////////////Order Messages///////////////////////////////////
    public ListOrderMessageResponse getOrderMessages(String id) {
        Order findOrder = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);
        List<OrderMessage> getMessages = orderMessageRepository
                .getMessagesOfAnOrder(findOrder);
        List<OrderMessageResponse> response = new ArrayList<>();
        for(OrderMessage om : getMessages) {
            response.add(OrderMessageResponse.of(om));
        }
        return ListOrderMessageResponse.of(response);
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
        OrderMessage findMessage = orderMessageRepository
                .findById(UUID.fromString(id)).orElseThrow(MessageNotFoundException::new);

            OrderMessage result = OrderMessage.builder()
                    .id(findMessage.getId())
                    .title(messageRequest.title())
                    .message(messageRequest.message())
                    .order(findMessage.getOrder())
                    .dateTime(findMessage.getDateTime())
                    .author(findMessage.getAuthor())
                    .build();
            orderMessageRepository.save(result);
            return OrderMessageResponse.of(result);
    }

    public void deleteOrderMessage(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        OrderMessage findMessage = orderMessageRepository
                .findById(UUID.fromString(id)).orElseThrow(MessageNotFoundException::new);
        if(findMessage.getAuthor() == user || Objects.equals(user.getUserRole().toString(), "[ADMIN]")) {
            orderMessageRepository.delete(findMessage);
        } else throw new NotEnoughPrivilegesException();
    }
}
