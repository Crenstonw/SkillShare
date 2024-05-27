package com.triana.salesianos.edu.skillshare.user.dto;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.model.UserRole;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserDetailsDto(
        UUID id,
        String email,
        String username,
        String name,
        String surname,
        String password,
        String profilePicture,
        Set<UserRole> role,
        List<OrderResponse> orders,
        List<OrderResponse> favoriteOrders,
        boolean enabled
) {
    public static UserDetailsDto of(User user) {
        List<OrderResponse> ordersResult = new ArrayList<>();
        for(Order order : user.getOrders()) {ordersResult.add(OrderResponse.of(order));}
        List<OrderResponse> favoriteOrdersResult = new ArrayList<>();
        for(Order order : user.getFavoriteOrders()){favoriteOrdersResult.add(OrderResponse.of(order));}
        return UserDetailsDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .profilePicture(user.getProfilePicture())
                .surname(user.getSurname())
                .password(user.getPassword())
                .role(user.getUserRole())
                .orders(ordersResult)
                .favoriteOrders(favoriteOrdersResult)
                .enabled(user.isEnabled())
                .build();
    }
}
