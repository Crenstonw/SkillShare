package com.triana.salesianos.edu.skillshare.user.dto;

import com.triana.salesianos.edu.skillshare.user.model.User;
import lombok.Builder;

@Builder
public record EditUserRequest(
        String name,
        String surname,
        String profilePicture,
        String username,
        String email
) {
    public static EditUserRequest of(User user) {
        return EditUserRequest.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .profilePicture(user.getProfilePicture())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

    }
}
