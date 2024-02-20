package com.triana.salesianos.edu.skillshare.user.dto;

public record CreateUserRequest(
        String email,
        String name,
        String surname,
        String password
) {
}
