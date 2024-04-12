package com.triana.salesianos.edu.skillshare.message.service;

import com.triana.salesianos.edu.skillshare.message.dto.DirectMessageResponse;
import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.message.repository.DirectMessageRepository;
import com.triana.salesianos.edu.skillshare.message.repository.OrderMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final DirectMessageRepository directMessageRepository;
    private final OrderMessageRepository orderMessageRepository;

    public DirectMessageResponse getDirectMessageById(String id) {
        Optional<DirectMessage> findMessage = directMessageRepository.findById(UUID.fromString(id));
        if (findMessage.isPresent()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(Objects.equals(findMessage.get().getUserFrom().getUsername(), userDetails.getUsername())) {
                
            } else {
                return null;
            }
            return DirectMessageResponse.of(findMessage.get());
        } else {
            return null;
        }
    }
}
