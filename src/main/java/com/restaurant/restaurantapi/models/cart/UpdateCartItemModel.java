package com.restaurant.restaurantapi.models.cart;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateCartItemModel {
    private Long cartItemId;
    private int quantity;
}
