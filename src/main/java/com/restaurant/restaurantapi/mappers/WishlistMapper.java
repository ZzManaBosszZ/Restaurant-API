package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.wishlist.WishlistDTO;
import com.restaurant.restaurantapi.entities.Wishlist;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper {
    public WishlistDTO toWishlistDTO(Wishlist model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        return WishlistDTO.builder()
                .id(model.getId())
                .userId(model.getUser() != null ? model.getUser().getId() : null)
                .foodId(model.getFood() != null ? model.getFood().getId() : null)
                .build();
    }
}
