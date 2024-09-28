package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.wishlist.WishlistDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.wishlist.CreateWishlistModel;
import com.restaurant.restaurantapi.services.impl.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<ResponseObject> createWishlist(@RequestBody CreateWishlistModel createWishlistModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject(false, 401, "User not authenticated", null)
            );
        }
        WishlistDTO wishlistDTO = wishlistService.createWishlist(createWishlistModel, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(true, 201, "Create Success", wishlistDTO )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistDTO> findWishlistById(@PathVariable Long id) {
        WishlistDTO wishlistDTO = wishlistService.findById(id);
        return ResponseEntity.ok(wishlistDTO);
    }

    @GetMapping
    public ResponseEntity<List<WishlistDTO>> findAllWishlists() {
        List<WishlistDTO> wishlistDTOs = wishlistService.findAll();
        return ResponseEntity.ok(wishlistDTOs);
    }
    @GetMapping("/food/{foodId}")
    public ResponseEntity<ResponseObject> findWishlistsByFood(@PathVariable Long foodId) {
        List<WishlistDTO> wishlistDTOs = wishlistService.findWishlistsByFood(foodId);
        return ResponseEntity.ok(
                new ResponseObject(true, 200, "Fetch Success", wishlistDTOs)
        );
    }
}
