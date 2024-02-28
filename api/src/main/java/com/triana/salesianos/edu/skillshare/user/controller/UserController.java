package com.triana.salesianos.edu.skillshare.user.controller;

import com.triana.salesianos.edu.skillshare.security.jwt.JwtProvider;
import com.triana.salesianos.edu.skillshare.user.dto.*;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/auth/register")
    public ResponseEntity<JwtUserResponse> createUSerWithUserRole(
            @RequestBody CreateUserRequest createUserRequest
    ) {
        User user = service.createUserWithUserRole(createUserRequest);
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                createUserRequest.email(),
                                createUserRequest.password()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        User userResponse = (User) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(userResponse, token));
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

    @GetMapping("/user")
    public ResponseEntity<List<AllUserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<AllUserResponse> getUser(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUser(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<AllUserResponse> editUser(@PathVariable String id, @RequestBody EditUserRequest editUserRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.editUser(id, editUserRequest));
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/user/favorite/{id}")
    public ResponseEntity<FavoriteDto> newFavoriteOrder(@PathVariable String id) {
        return ResponseEntity.ok().body(service.newFavoriteOrder(id));
    }
}
