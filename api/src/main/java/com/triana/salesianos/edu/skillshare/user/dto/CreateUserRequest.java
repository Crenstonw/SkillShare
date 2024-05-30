package com.triana.salesianos.edu.skillshare.user.dto;

public record CreateUserRequest(
        String email,
        String username,
        String name,
        String surname,
        String password
) {
}
