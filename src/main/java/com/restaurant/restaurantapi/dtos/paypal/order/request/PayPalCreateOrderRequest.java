package com.restaurant.restaurantapi.dtos.paypal.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalCreateOrderRequest {

    private String intent;

    @JsonProperty("purchase_units")
    private List<PayPalPurchaseUnitRequest> purchaseUnits;
}