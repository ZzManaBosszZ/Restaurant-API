package com.restaurant.restaurantapi.dtos.paypal.capture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayPalCapturePurchaseUnitResponse {

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("custom_id")
    private String customId;

    private PayPalPaymentsResponse payments;
}