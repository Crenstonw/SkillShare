package com.triana.salesianos.edu.skillshare.order.dto;
import java.util.Set;

public record NewOrderRequest(
        String title,
        String description,
        Set<String> tags
) {
}
