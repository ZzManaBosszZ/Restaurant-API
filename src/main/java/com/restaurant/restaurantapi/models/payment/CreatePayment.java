package com.restaurant.restaurantapi.models.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePayment {
    private Long id;
    private String paymentMethod;
    private Double price;
}
