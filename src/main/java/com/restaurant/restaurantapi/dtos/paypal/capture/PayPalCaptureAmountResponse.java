package com.restaurant.restaurantapi.dtos.paypal.capture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayPalCaptureAmountResponse {

    @JsonProperty("currency_code")
    private String currencyCode;

    private String value;
}