package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.payment.PaymentDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.payment.CreatePayment;
import com.restaurant.restaurantapi.services.impl.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    ResponseEntity<ResponseObject> insert(@RequestBody CreatePayment createPayment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        PaymentDTO paymentDTO = paymentService.payment(createPayment, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", paymentDTO)
        );
    }
}