package com.restaurant.restaurantapi.dtos.paypal.order.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayPalLinkResponse {

    private String href;

    private String rel;

    private String method;
}