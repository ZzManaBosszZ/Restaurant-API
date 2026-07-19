package com.restaurant.restaurantapi.dtos.paypal.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalPurchaseUnitRequest {

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("custom_id")
    private String customId;

    private String description;

    private PayPalMoneyRequest amount;
}