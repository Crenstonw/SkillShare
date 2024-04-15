package com.triana.salesianos.edu.skillshare.order.dto;

import java.util.List;

public record NewOrderRequest(
        String title,
        String description,
        List<String> tags
) {
}
