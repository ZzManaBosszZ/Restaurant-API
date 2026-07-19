package com.restaurant.restaurantapi.dtos.paypal.order.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PayPalCreateOrderApiResponse {

    private String id;

    private String status;

    private List<PayPalLinkResponse> links;
}