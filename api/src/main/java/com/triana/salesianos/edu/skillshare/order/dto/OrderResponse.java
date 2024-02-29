package com.triana.salesianos.edu.skillshare.order.dto;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.model.Tag;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import com.triana.salesianos.edu.skillshare.user.model.User;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;
@Builder
public record OrderResponse(
        UUID id,
        String title,
        String description,
        AllUserResponse user,
        Set<Tag> tags
) {

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .title(order.getTitle())
                .description(order.getDescription())
                .user(AllUserResponse.of(order.getUser()))
                .tags(order.getTags())
                .build();
    }
}
