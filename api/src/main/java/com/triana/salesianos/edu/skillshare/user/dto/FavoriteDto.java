package com.triana.salesianos.edu.skillshare.user.dto;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import com.triana.salesianos.edu.skillshare.user.model.User;

import java.util.Collection;

public record FavoriteDto (
        Collection<Order> favorites
) {

    public static  FavoriteDto of(User user) {
        return new FavoriteDto(
                user.getFavoriteOrders()
        );
    }
}
