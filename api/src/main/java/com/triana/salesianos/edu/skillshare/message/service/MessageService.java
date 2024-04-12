package com.triana.salesianos.edu.skillshare.message.service;

import com.triana.salesianos.edu.skillshare.message.dto.DirectMessageResponse;
import com.triana.salesianos.edu.skillshare.message.dto.ListDirectMessageResponse;
import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.message.repository.DirectMessageRepository;
import com.triana.salesianos.edu.skillshare.message.repository.OrderMessageRepository;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final DirectMessageRepository directMessageRepository;
    private final OrderMessageRepository orderMessageRepository;
    private final UserRepository userRepository;
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public ListDirectMessageResponse getMyDirectMessages() {
        Optional<User> user = userRepository.buscarPorUsername(userDetails.getUsername());
        List<DirectMessage> findMessages = directMessageRepository.findDirectMessagesByUserFrom(user.get());
        List<DirectMessageResponse> result = new ArrayList<>();
        for(DirectMessage dm : findMessages) {
            result.add(DirectMessageResponse.of(dm));
        }
        return ListDirectMessageResponse.of(result);
    }

    public DirectMessageResponse getDirectMessageById(String id) {
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
}
