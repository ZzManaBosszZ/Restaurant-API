package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.paypal.PayPalAccessTokenResponse;
import com.restaurant.restaurantapi.services.paypal.PayPalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PayPalTestController {

    private final PayPalClient payPalClient;

    @GetMapping("/test-connection")
    public ResponseEntity<ResponseObject> testConnection() {
        PayPalAccessTokenResponse tokenResponse =
                payPalClient.getAccessToken();

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("connected", true);
        data.put(
                "tokenType",
                tokenResponse.getTokenType()
        );
        data.put(
                "expiresIn",
                tokenResponse.getExpiresIn()
        );
        data.put(
                "appId",
                tokenResponse.getAppId()
        );

        return ResponseEntity.ok(
                new ResponseObject(
                        true,
                        200,
                        "Connected to PayPal Sandbox successfully",
                        data
                )
        );
    }
}