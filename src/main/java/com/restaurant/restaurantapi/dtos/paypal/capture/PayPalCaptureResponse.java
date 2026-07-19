package com.restaurant.restaurantapi.dtos.paypal.capture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayPalCaptureResponse {

    private String id;

    private String status;

    private PayPalCaptureAmountResponse amount;

    private Boolean finalCapture;
}