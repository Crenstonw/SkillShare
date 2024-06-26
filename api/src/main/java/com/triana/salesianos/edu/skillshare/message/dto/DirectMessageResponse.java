package com.triana.salesianos.edu.skillshare.message.dto;

import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.user.dto.UserResponse;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record DirectMessageResponse(
        UUID id,
        String title,
        String message,
        LocalDateTime dateTime,

        boolean isMyMessage,
        UserResponse userFrom,
        UserResponse userTo
) {
    public static DirectMessageResponse of(DirectMessage directMessage) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isMyMessage = directMessage.getUserFrom().getUsername().equals(userDetails.getUsername());

        return DirectMessageResponse.builder()
                .id(directMessage.getId())
                .title(directMessage.getTitle())
                .message(directMessage.getMessage())
                .dateTime(directMessage.getDateTime())
                .isMyMessage(isMyMessage)
                .userFrom(
                        UserResponse.of(directMessage.getUserFrom())
                )
                .userTo(
                        UserResponse.of(directMessage.getUserTo())
                )
                .build();
    }
}
