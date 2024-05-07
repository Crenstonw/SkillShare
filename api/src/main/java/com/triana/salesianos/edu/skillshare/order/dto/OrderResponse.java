package com.triana.salesianos.edu.skillshare.order.dto;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.OrderState;
import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Builder
public record OrderResponse(
        UUID id,
        String title,
        String description,

        OrderState state,
        LocalDateTime createdAt,
        LocalDateTime lastTimeModified,

        boolean isAboutToExpire,
        AllUserResponse user,
        Set<Tag> tags)
{

    public static OrderResponse of(Order order) {
        boolean isAboutToExpire = order.getLastTimeModified()
                .isBefore(LocalDateTime.now().minusMonths(1).minusDays(29));
        return OrderResponse.builder()
                .id(order.getId())
                .title(order.getTitle())
                .description(order.getDescription())
                .state(order.getState())
                .createdAt(order.getCreatedAt())
                .lastTimeModified(order.getLastTimeModified())
                .isAboutToExpire(isAboutToExpire)
                .user(AllUserResponse.of(order.getUser()))
                .tags(order.getTags())
                .build();
    }
}
