package com.triana.salesianos.edu.skillshare.order.dto;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import lombok.Builder;

import java.util.List;
@Builder
public record ListOrderResponse (
        List<OrderResponse> orders
) {

    public static ListOrderResponse of(List<OrderResponse> orders) {
        return ListOrderResponse.builder()
                .orders(orders)
                .build();
    }
}
