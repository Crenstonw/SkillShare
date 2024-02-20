package com.triana.salesianos.edu.skillshare.user.service;

import com.triana.salesianos.edu.skillshare.user.dto.CreateUserRequest;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.model.UserRole;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public User createUser(CreateUserRequest createUserRequest, Set<UserRole> roles) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .email(createUserRequest.email())
                .name(createUserRequest.name())
                .surname(createUserRequest.surname())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .username(createUserRequest.name() + createUserRequest.surname())
                .userRole(roles)
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, Set.of(UserRole.USER));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.buscarPorEmail(email);
    }
}
