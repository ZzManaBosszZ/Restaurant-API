package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.wishlist.WishlistDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.wishlist.CreateWishlistModel;

import java.util.List;

public interface WishlistService {
    WishlistDTO createWishlist(CreateWishlistModel createWishlistModel,User user);
    void deleteWishlist(Long id);
    WishlistDTO findById(Long id);
    List<WishlistDTO> findAll();
    List<WishlistDTO> findWishlistsByFood(Long foodId);
}
