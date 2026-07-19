package com.restaurant.restaurantapi.dtos.paypal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalCreateOrderResponse {

    private Long orderId;

    private Long paymentId;

    private String paypalOrderId;

    private String paypalStatus;

    private BigDecimal amount;

    private String currency;
}