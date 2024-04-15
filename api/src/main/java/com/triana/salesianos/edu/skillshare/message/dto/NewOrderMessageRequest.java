package com.triana.salesianos.edu.skillshare.message.dto;

public record NewOrderMessageRequest (
        String title,
        String message,

        String orderId
) {
}
