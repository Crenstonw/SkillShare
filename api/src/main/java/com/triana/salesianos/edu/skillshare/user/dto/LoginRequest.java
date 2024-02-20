package com.triana.salesianos.edu.skillshare.user.dto;

public record LoginRequest(
        String email,
        String password
) {
}