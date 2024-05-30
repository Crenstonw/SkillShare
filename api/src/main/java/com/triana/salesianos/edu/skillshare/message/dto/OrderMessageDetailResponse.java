package com.triana.salesianos.edu.skillshare.message.dto;

import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.user.dto.UserResponse;
import lombok.Builder;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderMessageDetailResponse (
        UUID id,
        String title,
        String message,
        Boolean isMyMessage,
        LocalDateTime dateTime,
        UserResponse author,
        OrderResponse order
) {
    public static OrderMessageDetailResponse of(OrderMessage message) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isMyMessage = message.getAuthor().getUsername().equals(userDetails.getUsername());
        UserResponse user = UserResponse.of(message.getAuthor());

        return OrderMessageDetailResponse.builder()
                .id(message.getId())
                .title(message.getTitle())
                .isMyMessage(isMyMessage)
                .message(message.getMessage())
                .dateTime(message.getDateTime())
                .author(user)
                .order(OrderResponse.of(message.getOrder()))
                .build();
    }
}
