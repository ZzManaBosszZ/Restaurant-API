package com.restaurant.restaurantapi.dtos.auth;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
}
