package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.payment.PaymentDTO;
import com.restaurant.restaurantapi.dtos.paypal.PayPalCaptureResult;
import com.restaurant.restaurantapi.dtos.paypal.PayPalCreateOrderResponse;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.payment.CreatePayment;

public interface PaymentService {

    PaymentDTO payment(CreatePayment createPayment, User currentUser);

    PayPalCreateOrderResponse createPayPalOrder(
            Long orderId,
            User currentUser
    );

    PayPalCaptureResult capturePayPalOrder(
            String paypalOrderId,
            User currentUser
    );

}
