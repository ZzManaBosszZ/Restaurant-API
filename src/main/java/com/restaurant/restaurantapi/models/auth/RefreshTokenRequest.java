package com.restaurant.restaurantapi.models.auth;


import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}