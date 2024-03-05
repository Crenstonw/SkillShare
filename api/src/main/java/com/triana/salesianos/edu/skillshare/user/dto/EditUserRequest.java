package com.triana.salesianos.edu.skillshare.user.dto;

import com.triana.salesianos.edu.skillshare.user.model.User;
import lombok.Builder;

@Builder
public record EditUserRequest(
        String name,
        String surname,
        String password,
        String profilePicture
) {
    public static EditUserRequest of(User user) {
        return EditUserRequest.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .password(user.getPassword())
                .profilePicture(user.getProfilePicture())
                .build();

    }
}
