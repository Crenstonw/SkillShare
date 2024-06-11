package com.triana.salesianos.edu.skillshare.order.dto;

import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.message.dto.OrderMessageResponse;
import com.triana.salesianos.edu.skillshare.message.model.OrderMessage;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.*;

@Builder
public record OrderDetailsResponse(
        UUID id,
        String title,
        String description,
        double price,

        OrderState state,
        LocalDateTime createdAt,
        LocalDateTime lastTimeModified,

        boolean isAboutToExpire,
        AllUserResponse user,
        Set<Tag> tags,
        List<OrderMessageResponse> messages
) {
    public static OrderDetailsResponse of(Order order) {
        boolean isAboutToExpire = order.getLastTimeModified()
                .isBefore(LocalDateTime.now().minusMonths(1).minusDays(29));
        List<OrderMessageResponse> messages = new ArrayList<>();
        for(OrderMessage orderMessage: order.getOrderMessages()) {
            messages.add(OrderMessageResponse.of(orderMessage));
        }
        Collections.reverse(messages);

        return OrderDetailsResponse.builder()
                .id(order.getId())
                .title(order.getTitle())
                .description(order.getDescription())
                .price(order.getPrice())
                .state(order.getState())
                .createdAt(order.getCreatedAt())
                .lastTimeModified(order.getLastTimeModified())
                .isAboutToExpire(isAboutToExpire)
                .user(AllUserResponse.of(order.getUser()))
                .tags(order.getTags())
                .messages(messages)
                .build();
    }

}
