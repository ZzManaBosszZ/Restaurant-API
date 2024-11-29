//package com.restaurant.restaurantapi.controllers;
//
//import com.restaurant.restaurantapi.dtos.ResponseObject;
//import com.restaurant.restaurantapi.dtos.cart.CartDTO;
//import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
//import com.restaurant.restaurantapi.models.cart.AddToCartModel;
//import com.restaurant.restaurantapi.models.cart.UpdateCartItemModel;
//import com.restaurant.restaurantapi.services.impl.CartService;
//import com.restaurant.restaurantapi.entities.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpSession;
//
//@RestController
//@RequestMapping("/api/v1/carts")
//@RequiredArgsConstructor
//public class CartController {
//
//    private final CartService cartService;
//
//    @GetMapping
//    public ResponseEntity<ResponseObject> getCart(HttpSession session) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) auth.getPrincipal();
//        CartDTO cartDTO = cartService.getCartByUser(currentUser, session);
//        return ResponseEntity.ok(
//                new ResponseObject(true, 200, "Fetch Cart Success", cartDTO)
//        );
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<ResponseObject> addToCart(@RequestBody AddToCartModel addToCartModel, HttpSession session) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) auth.getPrincipal();
//        CartDTO cartDTO = cartService.addToCart(addToCartModel, currentUser, session);
//        return ResponseEntity.ok(
//                new ResponseObject(true, 200, "Add To Cart Success", cartDTO)
//        );
//    }
//
//    @PostMapping("/update")
//    public ResponseEntity<ResponseObject> updateCartItem(@RequestBody UpdateCartItemModel updateCartItemModel, HttpSession session) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) auth.getPrincipal();
//        CartDTO cartDTO = cartService.updateCartItem(updateCartItemModel, currentUser, session);
//        return ResponseEntity.ok(
//                new ResponseObject(true, 200, "Update Cart Item Success", cartDTO)
//        );
//    }
//
//    @DeleteMapping("/remove/{cartItemId}")
//    public ResponseEntity<ResponseObject> removeCartItem(@PathVariable Long cartItemId, HttpSession session) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) auth.getPrincipal();
//        CartDTO cartDTO = cartService.removeCartItem(cartItemId, currentUser, session);
//        return ResponseEntity.ok(
//                new ResponseObject(true, 200, "Remove Cart Item Success", cartDTO)
//        );
//    }
//
//    @DeleteMapping("/clear")
//    public ResponseEntity<ResponseObject> clearCart(HttpSession session) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) auth.getPrincipal();
//        cartService.clearCart(currentUser, session);
//        return ResponseEntity.ok(
//                new ResponseObject(true, 200, "Clear Cart Success", null)
//        );
//    }
////    @PostMapping("/checkout")
////    public ResponseEntity<ResponseObject> checkoutCart(HttpSession session) {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        User currentUser = (User) auth.getPrincipal();
////
////        OrdersDTO order = cartService.checkoutCart(currentUser, session);
////        return ResponseEntity.ok(
////                new ResponseObject(true, 200, "Checkout Success", order)
////        );
////    }
//}
