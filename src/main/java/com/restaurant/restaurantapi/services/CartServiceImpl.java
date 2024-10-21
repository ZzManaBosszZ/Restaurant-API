//package com.restaurant.restaurantapi.services;
//
//import com.restaurant.restaurantapi.dtos.cart.CartDTO;
//import com.restaurant.restaurantapi.models.cart.AddToCartModel;
//import com.restaurant.restaurantapi.models.cart.UpdateCartItemModel;
//import com.restaurant.restaurantapi.entities.Cart;
//import com.restaurant.restaurantapi.entities.CartItem;
//import com.restaurant.restaurantapi.entities.Food;
//import com.restaurant.restaurantapi.entities.User;
//import com.restaurant.restaurantapi.exceptions.AppException;
//import com.restaurant.restaurantapi.exceptions.ErrorCode;
//import com.restaurant.restaurantapi.mappers.CartMapper;
//import com.restaurant.restaurantapi.mappers.CartItemMapper;
//import com.restaurant.restaurantapi.repositories.CartItemRepository;
//import com.restaurant.restaurantapi.repositories.CartRepository;
//import com.restaurant.restaurantapi.repositories.FoodRepository;
//import com.restaurant.restaurantapi.services.impl.CartService;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CartServiceImpl implements CartService {
//
//    private final CartRepository cartRepository;
//    private final CartItemRepository cartItemRepository;
//    private final FoodRepository foodRepository;
//    private final CartMapper cartMapper;
//    private final CartItemMapper cartItemMapper;
//
//    @Override
//    public CartDTO getCartByUser(User user, HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null || !cart.getUser().equals(user)) {
//            Optional<Cart> optionalCart = cartRepository.findByUser(user);
//            cart = optionalCart.orElseGet(() -> {
//                Cart newCart = new Cart();
//                newCart.setUser(user);
//                newCart.setCreatedBy(user.getUsername());
//                newCart.setModifiedBy(user.getUsername());
//                return cartRepository.save(newCart);
//            });
//            session.setAttribute("cart", cart);
//        }
//        return cartMapper.toCartDTO(cart);
//    }
//
//    @Override
//    public CartDTO addToCart(AddToCartModel addToCartModel, User user, HttpSession session) {
//        Cart cart = getCartEntityByUser(user, session);
//        CartItem existingItem = cart.getItems().stream()
//                .filter(item -> item.getFood().getId().equals(addToCartModel.getFoodId()))
//                .findFirst()
//                .orElse(null);
//
//        if (existingItem != null) {
//            existingItem.setQuantity(existingItem.getQuantity() + addToCartModel.getQuantity());
//            existingItem.setModifiedBy(user.getUsername());
//            cartItemRepository.save(existingItem);
//        } else {
//            Food food = foodRepository.findById(addToCartModel.getFoodId())
//                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
//            CartItem newItem = cartItemMapper.toCartItem(addToCartModel, cart, food);
//            newItem.setCreatedBy(user.getUsername());
//            newItem.setModifiedBy(user.getUsername());
//            cartItemRepository.save(newItem);
//            cart.getItems().add(newItem);
//        }
//        cart.setCreatedBy(user.getUsername());
//        cart.setModifiedBy(user.getUsername());
//        session.setAttribute("cart", cart);
//        return cartMapper.toCartDTO(cart);
//    }
//
//
//    @Override
//    public CartDTO updateCartItem(UpdateCartItemModel updateCartItemModel, User user, HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        CartItem item = cart.getItems().stream()
//                .filter(cartItem -> cartItem.getId().equals(updateCartItemModel.getCartItemId()))
//                .findFirst()
//                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
//        item.setQuantity(updateCartItemModel.getQuantity());
//        cart.setCreatedBy(user.getUsername());
//        cart.setModifiedBy(user.getUsername());
//        cartItemRepository.save(item);
//        session.setAttribute("cart", cart);
//        return cartMapper.toCartDTO(cart);
//    }
//
//    @Override
//    public CartDTO removeCartItem(Long cartItemId, User user, HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        CartItem item = cart.getItems().stream()
//                .filter(cartItem -> cartItem.getId().equals(cartItemId))
//                .findFirst()
//                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
//
//        cartItemRepository.delete(item);
//        cart.getItems().remove(item);
//        session.setAttribute("cart", cart);
//        return cartMapper.toCartDTO(cart);
//    }
//
//    @Override
//    public void clearCart(User user, HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        cartItemRepository.deleteAll(cart.getItems());
//        cart.getItems().clear();
//        cart.setModifiedBy(user.getUsername());
//        session.setAttribute("cart", cart);
//    }
//
//    @Override
//    public Cart getCartEntityByUser(User user, HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null || !cart.getUser().equals(user)) {
//            Optional<Cart> optionalCart = cartRepository.findByUser(user);
//            cart = optionalCart.orElseGet(() -> {
//                Cart newCart = new Cart();
//                newCart.setUser(user);
//                newCart.setCreatedBy(user.getUsername());
//                newCart.setModifiedBy(user.getUsername());
//                return cartRepository.save(newCart);
//            });
//            session.setAttribute("cart", cart);
//        }
//        return cart;
//    }
//
//
//}
