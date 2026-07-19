package com.restaurant.restaurantapi.dtos.paypal.capture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PayPalCaptureOrderApiResponse {

    private String id;

    private String status;

    @JsonProperty("purchase_units")
    private List<PayPalCapturePurchaseUnitResponse> purchaseUnits;
}