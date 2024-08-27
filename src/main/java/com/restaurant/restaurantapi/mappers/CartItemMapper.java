package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.cart.CartItemDTO;
import com.restaurant.restaurantapi.entities.Cart;
import com.restaurant.restaurantapi.entities.CartItem;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.models.cart.AddToCartModel;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        return CartItemDTO.builder()
                .id(cartItem.getId())
                .foodId(cartItem.getFood().getId())
                .foodName(cartItem.getFood().getName())
                .foodImage(cartItem.getFood().getImage())
                .foodPrice(cartItem.getFood().getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toCartItem(AddToCartModel addToCartModel, Cart cart, Food food) {
        return CartItem.builder()
                .cart(cart)
                .food(food)
                .quantity(addToCartModel.getQuantity())
                .build();
    }
}
