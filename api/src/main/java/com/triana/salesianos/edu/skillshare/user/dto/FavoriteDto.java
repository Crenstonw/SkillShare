package com.triana.salesianos.edu.skillshare.user.dto;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.user.model.User;
import lombok.Builder;

import java.util.Collection;
@Builder
public record FavoriteDto (
        OrderResponse favorite
) {
    public static  FavoriteDto of(Order order) {
        return FavoriteDto.builder()
                .favorite(OrderResponse.of(order))
                .build();
    }
}
