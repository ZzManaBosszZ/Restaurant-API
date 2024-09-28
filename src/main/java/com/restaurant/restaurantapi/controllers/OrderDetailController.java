package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;

import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.services.impl.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        OrderDetailDTO orderDetails = orderDetailService.findByOrderId(orderId, currenUser);
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
        OrderDetailDTO orderDetail = orderDetailService.findById(id);
        return ResponseEntity.ok(orderDetail);
    }
}
