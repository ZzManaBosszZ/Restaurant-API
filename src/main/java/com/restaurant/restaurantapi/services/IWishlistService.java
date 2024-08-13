package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.wishlist.WishlistDTO;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.entities.Wishlist;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.WishlistMapper;

import com.restaurant.restaurantapi.models.wishlist.CreateWishlistModel;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.repositories.UserRepository;
import com.restaurant.restaurantapi.repositories.WishlistRepository;
import com.restaurant.restaurantapi.services.impl.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IWishlistService implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistMapper wishlistMapper;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Override
    public WishlistDTO createWishlist(CreateWishlistModel createWishlistModel,User user ) {
        Food food = foodRepository.findById(createWishlistModel.getFoodId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .food(food)
                .build();
        return wishlistMapper.toWishlistDTO(wishlistRepository.save(wishlist));
    }

    @Override
    public void deleteWishlist(Long id) {
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));

        wishlistRepository.delete(wishlist);
    }

    @Override
    public WishlistDTO findById(Long id) {
        return wishlistRepository.findById(id)
                .map(wishlistMapper::toWishlistDTO)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
    }

    @Override
    public List<WishlistDTO> findAll() {
        return wishlistRepository.findAll().stream()
                .map(wishlistMapper::toWishlistDTO)
                .collect(Collectors.toList());
    }

    public List<WishlistDTO> findWishlistsByFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        List<Wishlist> wishlists = wishlistRepository.findByFood(food);
        return wishlists.stream()
                .map(wishlistMapper::toWishlistDTO)
                .collect(Collectors.toList());
    }
}
