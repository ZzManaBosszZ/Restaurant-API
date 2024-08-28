package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.cart.CartDTO;
import com.restaurant.restaurantapi.dtos.cart.CartItemDTO;
import com.restaurant.restaurantapi.entities.Cart;
import com.restaurant.restaurantapi.entities.CartItem;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDTO toCartDTO(Cart cart) {
        if (cart == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
                .map(this::toCartItemDTO)
                .collect(Collectors.toList());

        return CartDTO.builder()
                .id(cart.getId())
                .items(cartItemDTOs)
                .createdBy(cart.getCreatedBy())
                .modifiedBy(cart.getModifiedBy())
                .build();
    }

    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        if (cartItem == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        Food food = cartItem.getFood();
        return CartItemDTO.builder()
                .foodId(food.getId())
                .foodName(food.getName())
                .foodImage(food.getImage())
                .foodPrice(food.getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
