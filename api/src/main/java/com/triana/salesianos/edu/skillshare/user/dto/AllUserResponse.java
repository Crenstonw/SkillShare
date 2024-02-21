package com.triana.salesianos.edu.skillshare.user.dto;

import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.model.UserRole;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record AllUserResponse(
        String email,
        String username,
        String name,
        String surname,
        String password,
        String userRole,
        LocalDateTime createdAt,
        boolean enabled
) {

    public static AllUserResponse of(User user) {
        return AllUserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .password(user.getPassword())
                .userRole(user.getUserRole().toString())
                .createdAt(user.getCreatedAt())
                .enabled(user.isEnabled())
                .build();
    }
}
