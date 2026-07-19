package com.restaurant.restaurantapi.models.orders;

import com.restaurant.restaurantapi.models.food.FoodQuantity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrders {

    private List<FoodQuantity> foodQuantities;

    private BigDecimal discount;

    private String paymentMethod;

    private String phone;

    private String address;
}