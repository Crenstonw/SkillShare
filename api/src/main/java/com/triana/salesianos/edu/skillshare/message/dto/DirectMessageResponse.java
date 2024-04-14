package com.triana.salesianos.edu.skillshare.message.dto;

import com.triana.salesianos.edu.skillshare.message.model.DirectMessage;
import com.triana.salesianos.edu.skillshare.user.dto.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DirectMessageResponse(
        String title,
        String message,
        LocalDateTime dateTime,
        UserResponse userFrom,
        UserResponse userTo
) {
    public static DirectMessageResponse of(DirectMessage directMessage) {
        return DirectMessageResponse.builder()
                .title(directMessage.getTitle())
                .message(directMessage.getMessage())
                .dateTime(directMessage.getDateTime())
                .userFrom(
                        UserResponse.fromUser(directMessage.getUserFrom())
                )
                .userTo(
                        UserResponse.fromUser(directMessage.getUserTo())
                )
                .build();
    }
}
