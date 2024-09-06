package com.restaurant.restaurantapi.models.orders;

import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.models.food.FoodQuantity;
import com.restaurant.restaurantapi.models.orderdetail.CreateOrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrders {
    private List<FoodQuantity> foodQuantities;

    // Discount to be applied to the order
    private BigDecimal discount;
}
