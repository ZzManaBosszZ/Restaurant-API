package com.restaurant.restaurantapi.services.impl;
import com.restaurant.restaurantapi.dtos.cart.CartDTO;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.entities.Cart;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.cart.AddToCartModel;
import com.restaurant.restaurantapi.models.cart.UpdateCartItemModel;
import jakarta.servlet.http.HttpSession;

public interface CartService {
    CartDTO getCartByUser(User user, HttpSession session);
    CartDTO addToCart(AddToCartModel addToCartModel, User user, HttpSession session);
    CartDTO updateCartItem(UpdateCartItemModel updateCartItemModel, User user, HttpSession session);
    CartDTO removeCartItem(Long cartItemId, User user, HttpSession session);
    void clearCart(User user, HttpSession session);
    Cart getCartEntityByUser(User user, HttpSession session);
//    OrdersDTO checkoutCart(User currentUser, HttpSession session);
}

