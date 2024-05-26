package com.triana.salesianos.edu.skillshare.user.controller;

import com.triana.salesianos.edu.skillshare.security.jwt.JwtProvider;
import com.triana.salesianos.edu.skillshare.user.dto.*;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<Page<AllUserResponse>> getAllUsers(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers(pageable));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable String id) {
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
    public ResponseEntity<List<FavoriteDto>> newFavoriteOrder(@PathVariable String id) {
        return ResponseEntity.ok().body(service.newFavoriteOrder(id));
    }

    @PutMapping("/user/unfavorite/{id}")
    public ResponseEntity<List<FavoriteDto>> deleteFavoriteOrder(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteFavoriteOrder(id));
    }

    @GetMapping("/user/me")
    public ResponseEntity<AllUserResponse> actualUserInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(service.actualUserInfo());
    }

    @PutMapping("/user/ban/{id}")
    public ResponseEntity<UserDetailsDto> banUser(@PathVariable String id) {
        return ResponseEntity.ok().body(service.banUser(id));
    }
}
