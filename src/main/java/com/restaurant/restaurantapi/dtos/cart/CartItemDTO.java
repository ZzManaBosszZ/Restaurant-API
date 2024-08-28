package com.restaurant.restaurantapi.dtos.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {
    private Long id;
    private Long foodId;
    private String foodName;
    private String foodImage;
    private double foodPrice;
    private int quantity;
    private String createdBy;
    private String modifiedBy;
}
