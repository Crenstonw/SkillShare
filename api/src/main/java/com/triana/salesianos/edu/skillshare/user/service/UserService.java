package com.triana.salesianos.edu.skillshare.user.service;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.exception.NoOrderException;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import com.triana.salesianos.edu.skillshare.security.errorhandling.JwtTokenException;
import com.triana.salesianos.edu.skillshare.user.dto.*;
import com.triana.salesianos.edu.skillshare.user.exception.CannotBanYourself;
import com.triana.salesianos.edu.skillshare.user.exception.CannotModifyPrivileges;
import com.triana.salesianos.edu.skillshare.user.exception.FavoriteInterpolationNotPosibleException;
import com.triana.salesianos.edu.skillshare.user.exception.UserNotFound;
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
import java.util.stream.Stream;

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
                .username(createUserRequest.username())
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

    public UserDetailsDto editUser(String id, EditUserRequest editUserRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFound::new);
        Optional<User> checkEmail = userRepository.findByEmail(editUserRequest.email());
        Optional<User> checkUsername = userRepository.findByUsername(editUserRequest.username());
        if(checkEmail.isPresent() && !Objects.equals(checkEmail.get().getEmail(), user.getEmail())) throw new RuntimeException("Email is being used");
        if(checkUsername.isPresent() && !Objects.equals(checkUsername.get().getUsername(), user.getUsername())) throw new RuntimeException("Username is being used");
        if(Objects.equals(authenticatedUser.getUserRole().toString(), "[ADMIN]") || user.getId() == UUID.fromString(id)) {
            user.setEmail(editUserRequest.email());
            user.setName(editUserRequest.name());
            user.setSurname(editUserRequest.surname());
            user.setProfilePicture(editUserRequest.profilePicture());
            user.setUsername(editUserRequest.username());
            userRepository.save(user);
            return UserDetailsDto.of(user);
        } else throw new RuntimeException("Not allowed to edit user");
    }

    public void deleteUser(String id) {
        Optional<User> findUser = userRepository.findById(UUID.fromString(id));
        findUser.ifPresent(userRepository::delete);
    }

    public List<OrderResponse> myFavorites() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        return user.getFavoriteOrders().stream().map(OrderResponse::of).toList();
    }

    public FavoriteDto newFavoriteOrder(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Order order = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);
        List<Order> newFavoriteList = user.getFavoriteOrders();
        for(Order forOrder : newFavoriteList) {
            if(forOrder == order) {
                throw new FavoriteInterpolationNotPosibleException();
            }
        }
        newFavoriteList.add(order);
        user.setFavoriteOrders(newFavoriteList);
        userRepository.save(user);
        return FavoriteDto.of(order);
    }

    public FavoriteDto deleteFavoriteOrder(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);
        Order order = orderRepository.findById(UUID.fromString(id)).orElseThrow(NoOrderException::new);

        List<Order> newFavoriteList = user.getFavoriteOrders();
        newFavoriteList.removeIf(forOrder -> Objects.equals(forOrder, order));

        user.setFavoriteOrders(newFavoriteList);
        userRepository.save(user);
        return FavoriteDto.of(order);
    }

    public AllUserResponse actualUserInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFound::new);

        return AllUserResponse.of(user);
    }

    public UserDetailsDto givePrivileges(String id) {
        UserDetails currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByUsername(currentUserDetails.getUsername())
                .orElseThrow(UserNotFound::new);

        if (!currentUser.getUserRole().contains(UserRole.ADMIN)) {
            throw new RuntimeException("You do not have permission to change user roles.");
        }

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFound::new);

        if (currentUser.getUsername().equals(user.getUsername())) throw new CannotModifyPrivileges();

        Set<UserRole> newRoles = new HashSet<>(user.getUserRole());
        if (newRoles.contains(UserRole.USER)) {
            newRoles.clear();
            newRoles.add(UserRole.ADMIN);
        } else {
            newRoles.clear();
            newRoles.add(UserRole.USER);
        }

        user.setUserRole(newRoles);
        userRepository.save(user);
        return UserDetailsDto.of(user);
    }

    public UserDetailsDto banUser(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFound::new);
        if(!Objects.equals(userDetails.getUsername(), user.getUsername())) {
            user.setEnabled(!user.getEnabled());
            userRepository.save(user);
            return UserDetailsDto.of(user);
        } else throw new CannotBanYourself();
    }
}
