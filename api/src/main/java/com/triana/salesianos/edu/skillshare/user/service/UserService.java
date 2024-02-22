package com.triana.salesianos.edu.skillshare.user.service;

import com.triana.salesianos.edu.skillshare.security.errorhandling.JwtTokenException;
import com.triana.salesianos.edu.skillshare.user.dto.AllUserResponse;
import com.triana.salesianos.edu.skillshare.user.dto.CreateUserRequest;
import com.triana.salesianos.edu.skillshare.user.dto.EditUserRequest;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.model.UserRole;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    public List<AllUserResponse> getAllUsers() {
        List<User> findAll = userRepository.findAll();
        List<AllUserResponse> result = new ArrayList<>();

        for(User user : findAll) {
            result.add(AllUserResponse.of(user));
        }

        return result;
    }

    public AllUserResponse getUser(String id) {
        Optional<User> findUser = userRepository.findById(UUID.fromString(id));
        if(findUser.isPresent()) {
            return AllUserResponse.of(findUser.get());
        } else throw new JwtTokenException("si");
    }

    public AllUserResponse editUser(String id, EditUserRequest editUserRequest) {
        Optional<User> findUser = userRepository.findById(UUID.fromString(id));
        if(findUser.isPresent()) {
            findUser.get().setName(editUserRequest.name());
            findUser.get().setSurname(editUserRequest.surname());
            findUser.get().setPassword(passwordEncoder.encode(editUserRequest.password()));

            userRepository.save(findUser.get());

            return AllUserResponse.of(findUser.get());
        } else {
            return null;
        }
    }

    public void deleteUser(String id) {
        Optional<User> findUser = userRepository.findById(UUID.fromString(id));
        findUser.ifPresent(userRepository::delete);
    }
}
