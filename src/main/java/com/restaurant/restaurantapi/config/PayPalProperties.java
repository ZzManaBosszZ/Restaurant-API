package com.restaurant.restaurantapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "paypal")
public class PayPalProperties {

    private String baseUrl;

    private String clientId;

    private String clientSecret;
}