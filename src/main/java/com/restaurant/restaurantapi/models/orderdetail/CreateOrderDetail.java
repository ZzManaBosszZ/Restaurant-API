package com.restaurant.restaurantapi.models.orderdetail;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderDetail {
    private Long foodId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;


}
