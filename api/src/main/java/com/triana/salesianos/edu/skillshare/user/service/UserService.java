package com.triana.salesianos.edu.skillshare.user.service;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.security.errorhandling.JwtTokenException;
import com.triana.salesianos.edu.skillshare.user.dto.*;
import com.triana.salesianos.edu.skillshare.user.exception.CannotBanYourself;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.model.UserRole;
import com.triana.salesianos.edu.skillshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    public User createUser(CreateUserRequest createUserRequest, Set<UserRole> roles) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .email(createUserRequest.email())
                .name(createUserRequest.name())
                .surname(createUserRequest.surname())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .username(createUserRequest.name() + createUserRequest.surname())
                .profilePicture("https://as2.ftcdn.net/v2/jpg/03/49/49/79/1000_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg")
                .userRole(roles)
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, Set.of(UserRole.USER));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Page<AllUserResponse> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAllUsers(pageable);

        return userPage.map(AllUserResponse::of);
    }

    public UserDetailsDto getUser(String id) {
        Optional<User> findUser = userRepository.findById(UUID.fromString(id));
        if(findUser.isPresent()) {
            return UserDetailsDto.of(findUser.get());
        } else throw new JwtTokenException("User not found");
    }

    public AllUserResponse editUser(String id, EditUserRequest editUserRequest) {
        Optional<User> findUser = userRepository.findById(UUID.fromString(id));
        if(findUser.isPresent()) {
            findUser.get().setName(editUserRequest.name());
            findUser.get().setSurname(editUserRequest.surname());
            findUser.get().setPassword(passwordEncoder.encode(editUserRequest.password()));
            findUser.get().setProfilePicture(editUserRequest.profilePicture());

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

    public List<FavoriteDto> newFavoriteOrder(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NoOrderException::new);
        Order order = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);
        List<Order> newFavoriteList = user.getFavoriteOrders();
        newFavoriteList.add(order);
        List<FavoriteDto> result = new ArrayList<>();
        user.setFavoriteOrders(newFavoriteList);
        userRepository.save(user);
        for(Order forOrder : newFavoriteList) {
            result.add(FavoriteDto.of(forOrder));
        }
        return result;
    }

    public List<FavoriteDto> deleteFavoriteOrder(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NoOrderException::new);
        Order order = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);

        List<Order> newFavoriteList = user.getFavoriteOrders();
        newFavoriteList.removeIf(forOrder -> Objects.equals(forOrder, order));

        user.setFavoriteOrders(newFavoriteList);
        userRepository.save(user);

        List<FavoriteDto> result = new ArrayList<>();
        for(Order forOrder : newFavoriteList) {
            result.add(FavoriteDto.of(forOrder));
        }
        return result;
    }

    public AllUserResponse actualUserInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NoOrderException::new);

        return AllUserResponse.of(user);
    }

    public UserDetailsDto banUser(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);
        if(!Objects.equals(userDetails.getUsername(), user.getUsername())) {
            user.setEnabled(!user.getEnabled());
            userRepository.save(user);
            return UserDetailsDto.of(user);
        } else throw new CannotBanYourself();

    }
}
