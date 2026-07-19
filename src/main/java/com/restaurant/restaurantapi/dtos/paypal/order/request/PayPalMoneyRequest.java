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
public class PayPalMoneyRequest {

    @JsonProperty("currency_code")
    private String currencyCode;

    private String value;
}