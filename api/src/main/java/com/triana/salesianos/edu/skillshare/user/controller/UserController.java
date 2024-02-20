package com.triana.salesianos.edu.skillshare.user.controller;

import com.triana.salesianos.edu.skillshare.security.jwt.JwtProvider;
import com.triana.salesianos.edu.skillshare.user.dto.CreateUserRequest;
import com.triana.salesianos.edu.skillshare.user.dto.JwtUserResponse;
import com.triana.salesianos.edu.skillshare.user.dto.LoginRequest;
import com.triana.salesianos.edu.skillshare.user.dto.UserResponse;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class UserController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUSerWithUserRole(
            @RequestBody CreateUserRequest createUserRequest
    ) {
        User user = service.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.email(),
                                loginRequest.password()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(user, token));

    }
}
