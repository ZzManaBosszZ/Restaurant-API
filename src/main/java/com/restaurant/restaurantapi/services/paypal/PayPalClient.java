package com.restaurant.restaurantapi.services.paypal;

import com.restaurant.restaurantapi.config.PayPalProperties;
import com.restaurant.restaurantapi.dtos.paypal.PayPalAccessTokenResponse;
import com.restaurant.restaurantapi.dtos.paypal.capture.PayPalCaptureOrderApiResponse;
import com.restaurant.restaurantapi.dtos.paypal.order.request.PayPalCreateOrderRequest;
import com.restaurant.restaurantapi.dtos.paypal.order.request.PayPalMoneyRequest;
import com.restaurant.restaurantapi.dtos.paypal.order.request.PayPalPurchaseUnitRequest;
import com.restaurant.restaurantapi.dtos.paypal.order.response.PayPalCreateOrderApiResponse;
import com.restaurant.restaurantapi.entities.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayPalClient {

    private final RestTemplate restTemplate;

    private final PayPalProperties payPalProperties;

    public PayPalAccessTokenResponse getAccessToken() {
        validateConfiguration();

        String url = payPalProperties.getBaseUrl()
                + "/v1/oauth2/token";

        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth(
                payPalProperties.getClientId(),
                payPalProperties.getClientSecret(),
                StandardCharsets.UTF_8
        );

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        headers.setAccept(
                Collections.singletonList(
                        MediaType.APPLICATION_JSON
                )
        );

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add(
                "grant_type",
                "client_credentials"
        );

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<PayPalAccessTokenResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            PayPalAccessTokenResponse.class
                    );

            PayPalAccessTokenResponse responseBody =
                    response.getBody();

            if (!response.getStatusCode().is2xxSuccessful()
                    || responseBody == null
                    || responseBody.getAccessToken() == null
                    || responseBody.getAccessToken().isBlank()) {

                throw new IllegalStateException(
                        "PayPal did not return an access token"
                );
            }

            return responseBody;

        } catch (HttpClientErrorException exception) {
            throw new IllegalStateException(
                    "PayPal authentication rejected: "
                            + exception.getStatusCode(),
                    exception
            );

        } catch (HttpServerErrorException exception) {
            throw new IllegalStateException(
                    "PayPal server error: "
                            + exception.getStatusCode(),
                    exception
            );

        } catch (RestClientException exception) {
            throw new IllegalStateException(
                    "Cannot connect to PayPal",
                    exception
            );
        }
    }

    private void validateConfiguration() {
        if (payPalProperties.getBaseUrl() == null
                || payPalProperties.getBaseUrl().isBlank()) {
            throw new IllegalStateException(
                    "paypal.base-url is missing"
            );
        }

        if (payPalProperties.getClientId() == null
                || payPalProperties.getClientId().isBlank()) {
            throw new IllegalStateException(
                    "PAYPAL_CLIENT_ID is missing"
            );
        }

        if (payPalProperties.getClientSecret() == null
                || payPalProperties.getClientSecret().isBlank()) {
            throw new IllegalStateException(
                    "PAYPAL_CLIENT_SECRET is missing"
            );
        }
    }

    public PayPalCreateOrderApiResponse createOrder(
            Payment payment
    ) {
        PayPalAccessTokenResponse tokenResponse =
                getAccessToken();

        String url = payPalProperties.getBaseUrl().trim()
                + "/v2/checkout/orders";

        String amount = payment.getPrice()
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString();

        String internalOrderReference =
                "ORDER-" + payment.getOrder().getId();

        PayPalMoneyRequest money =
                PayPalMoneyRequest.builder()
                        .currencyCode(payment.getCurrency())
                        .value(amount)
                        .build();

        PayPalPurchaseUnitRequest purchaseUnit =
                PayPalPurchaseUnitRequest.builder()
                        .referenceId(internalOrderReference)
                        .customId(
                                String.valueOf(
                                        payment.getOrder().getId()
                                )
                        )
                        .description(
                                "Restaurant order "
                                        + payment.getOrder().getId()
                        )
                        .amount(money)
                        .build();

        PayPalCreateOrderRequest requestBody =
                PayPalCreateOrderRequest.builder()
                        .intent("CAPTURE")
                        .purchaseUnits(
                                List.of(purchaseUnit)
                        )
                        .build();

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(
                tokenResponse.getAccessToken()
        );

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setAccept(
                List.of(MediaType.APPLICATION_JSON)
        );

        headers.set(
                "PayPal-Request-Id",
                payment.getPaypalRequestId()
        );

        HttpEntity<PayPalCreateOrderRequest> request =
                new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<PayPalCreateOrderApiResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            PayPalCreateOrderApiResponse.class
                    );

            PayPalCreateOrderApiResponse responseBody =
                    response.getBody();

            if (!response.getStatusCode().is2xxSuccessful()
                    || responseBody == null
                    || responseBody.getId() == null
                    || responseBody.getId().isBlank()) {

                throw new IllegalStateException(
                        "PayPal did not return an order ID"
                );
            }

            return responseBody;

        } catch (HttpClientErrorException exception) {
            throw new IllegalStateException(
                    "PayPal create order rejected: "
                            + exception.getStatusCode()
                            + " - "
                            + exception.getResponseBodyAsString(),
                    exception
            );

        } catch (HttpServerErrorException exception) {
            throw new IllegalStateException(
                    "PayPal server error while creating order: "
                            + exception.getStatusCode(),
                    exception
            );

        } catch (RestClientException exception) {
            throw new IllegalStateException(
                    "Cannot create PayPal order: "
                            + exception.getMessage(),
                    exception
            );
        }
    }

    public PayPalCaptureOrderApiResponse captureOrder(
            String paypalOrderId,
            String requestId
    ) {
        PayPalAccessTokenResponse tokenResponse =
                getAccessToken();

        String url = payPalProperties.getBaseUrl().trim()
                + "/v2/checkout/orders/"
                + paypalOrderId
                + "/capture";

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(
                tokenResponse.getAccessToken()
        );

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        headers.setAccept(
                List.of(MediaType.APPLICATION_JSON)
        );

        headers.set(
                "PayPal-Request-Id",
                requestId
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(
                        Collections.emptyMap(),
                        headers
                );

        try {
            ResponseEntity<PayPalCaptureOrderApiResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            PayPalCaptureOrderApiResponse.class
                    );

            PayPalCaptureOrderApiResponse body =
                    response.getBody();

            if (!response.getStatusCode().is2xxSuccessful()
                    || body == null) {
                throw new IllegalStateException(
                        "PayPal did not return capture data"
                );
            }

            return body;

        } catch (HttpClientErrorException exception) {
            throw new IllegalStateException(
                    "PayPal capture rejected: "
                            + exception.getStatusCode()
                            + " - "
                            + exception.getResponseBodyAsString(),
                    exception
            );

        } catch (HttpServerErrorException exception) {
            throw new IllegalStateException(
                    "PayPal server error while capturing: "
                            + exception.getStatusCode()
                            + " - "
                            + exception.getResponseBodyAsString(),
                    exception
            );

        } catch (RestClientException exception) {
            throw new IllegalStateException(
                    "Cannot capture PayPal order: "
                            + exception.getMessage(),
                    exception
            );
        }
    }
}