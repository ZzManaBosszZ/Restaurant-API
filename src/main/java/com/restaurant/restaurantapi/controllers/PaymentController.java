package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.payment.PaymentDTO;
import com.restaurant.restaurantapi.dtos.paypal.PayPalCaptureResult;
import com.restaurant.restaurantapi.dtos.paypal.PayPalCreateOrderResponse;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.payment.CreatePayment;
import com.restaurant.restaurantapi.services.impl.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<ResponseObject> insert(
            @Valid @RequestBody CreatePayment createPayment
    ) {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        User currentUser =
                (User) authentication.getPrincipal();

        PaymentDTO paymentDTO =
                paymentService.payment(
                        createPayment,
                        currentUser
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseObject(
                                true,
                                201,
                                "Payment initialized",
                                paymentDTO
                        )
                );
    }

    @PostMapping("/payments/paypal/orders/{orderId}")
    public ResponseEntity<ResponseObject> createPayPalOrder(
            @PathVariable Long orderId
    ) {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        User currentUser =
                (User) authentication.getPrincipal();

        PayPalCreateOrderResponse result =
                paymentService.createPayPalOrder(
                        orderId,
                        currentUser
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseObject(
                                true,
                                201,
                                "PayPal order created",
                                result
                        )
                );
    }

    @PostMapping(
            "/payments/paypal/orders/{paypalOrderId}/capture"
    )
    public ResponseEntity<ResponseObject> capturePayPalOrder(
            @PathVariable String paypalOrderId
    ) {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        User currentUser =
                (User) authentication.getPrincipal();

        PayPalCaptureResult result =
                paymentService.capturePayPalOrder(
                        paypalOrderId,
                        currentUser
                );

        return ResponseEntity.ok(
                new ResponseObject(
                        true,
                        200,
                        "PayPal payment captured successfully",
                        result
                )
        );
    }

//    @PostMapping("/payment")
//    ResponseEntity<ResponseObject> insert(@RequestBody CreatePayment createPayment) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) auth.getPrincipal();
//        PaymentDTO paymentDTO = paymentService.payment(createPayment, currentUser);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(true, 200, "ok", paymentDTO)
//        );
//    }
}