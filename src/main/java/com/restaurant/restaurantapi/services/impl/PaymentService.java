package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.payment.PaymentDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.payment.CreatePayment;

public interface PaymentService {

    PaymentDTO payment(CreatePayment createPayment, User currentUser);

}
