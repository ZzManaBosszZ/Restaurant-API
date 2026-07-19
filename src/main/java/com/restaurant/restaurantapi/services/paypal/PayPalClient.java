package com.restaurant.restaurantapi.services.paypal;

import com.restaurant.restaurantapi.config.PayPalProperties;
import com.restaurant.restaurantapi.dtos.paypal.PayPalAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

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
}