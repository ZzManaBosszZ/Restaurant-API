package com.restaurant.restaurantapi.models.cart;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddToCartModel {
    private Long foodId;
    private int quantity;
}
