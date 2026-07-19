package com.restaurant.restaurantapi.dtos.paypal.capture;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PayPalPaymentsResponse {

    private List<PayPalCaptureResponse> captures;
}