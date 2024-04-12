package com.triana.salesianos.edu.skillshare.message.dto;

public record NewDirectMessageRequest(
        String title,
        String message,
        String toUserEmail
) {
}
