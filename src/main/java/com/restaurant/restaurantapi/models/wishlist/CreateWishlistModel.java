package com.restaurant.restaurantapi.models.wishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWishlistModel {
//    private Long userId;
    private Long foodId;
}
