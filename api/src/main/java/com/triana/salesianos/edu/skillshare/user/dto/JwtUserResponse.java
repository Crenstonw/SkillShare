package com.triana.salesianos.edu.skillshare.user.dto;

import com.triana.salesianos.edu.skillshare.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class JwtUserResponse extends UserResponse {
    private String token;

    public JwtUserResponse(UserResponse userResponse) {
        this.id = userResponse.getId();
        this.username = userResponse.getUsername();
        this.email = userResponse.getEmail();
        this.createdAt = userResponse.getCreatedAt();
        this.isAdmin = userResponse.isAdmin();
    }

    public static JwtUserResponse of (User user, String token) {
        JwtUserResponse result = new JwtUserResponse(UserResponse.of(user));
        result.setToken(token);
        return result;
    }
}
