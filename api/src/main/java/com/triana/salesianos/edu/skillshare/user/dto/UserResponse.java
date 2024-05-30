package com.triana.salesianos.edu.skillshare.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triana.salesianos.edu.skillshare.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    protected String id;
    protected String email, username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;
    protected boolean isAdmin;

    public static UserResponse of(User user) {
        boolean isAdmin = Objects.equals(user.getUserRole().toString(), "[ADMIN]");
        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .isAdmin(isAdmin)
                .build();
    }
}
