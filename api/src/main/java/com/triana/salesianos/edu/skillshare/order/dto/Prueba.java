package com.triana.salesianos.edu.skillshare.order.dto;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import lombok.Builder;

import java.util.UUID;

@Builder
public record Prueba(
        UUID id
) {
    public static Prueba of(Order order) {
        return Prueba.builder()
                .id(order.getId())
                .build();
    }
}
