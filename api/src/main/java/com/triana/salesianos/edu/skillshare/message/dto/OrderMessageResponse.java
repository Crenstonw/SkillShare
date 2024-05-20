package com.triana.salesianos.edu.skillshare.message.dto;

import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.user.dto.UserResponse;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
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
        UserResponse user = UserResponse.of(message.getAuthor());

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
