package com.restaurant.restaurantapi.models.payment;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CreatePayment {
//    private Long id;
//    private String paymentMethod;
//    private Double price;

    @NotNull(message = "Order ID must not be null")
    private Long orderId;

    @NotBlank(message = "Payment method must not be blank")
    private String paymentMethod;
}

