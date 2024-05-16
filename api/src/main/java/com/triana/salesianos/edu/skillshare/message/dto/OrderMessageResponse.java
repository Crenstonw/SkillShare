package com.triana.salesianos.edu.skillshare.message.dto;

import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.message.repository.OrderMessageRepository;
import com.triana.salesianos.edu.skillshare.user.dto.UserResponse;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record OrderMessageResponse(
        UUID id,
        String title,
        String message,
        Boolean isMyMessage,
        LocalDateTime dateTime,
        UserResponse author


) {
    public static OrderMessageResponse of(OrderMessage message) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isMyMessage = message.getAuthor().getUsername().equals(userDetails.getUsername());
        UserResponse user = UserResponse.fromUser(message.getAuthor());

        return OrderMessageResponse.builder()
                .id(message.getId())
                .title(message.getTitle())
                .isMyMessage(isMyMessage)
                .message(message.getMessage())
                .dateTime(message.getDateTime())
                .author(user)
                .build();
    }
}
