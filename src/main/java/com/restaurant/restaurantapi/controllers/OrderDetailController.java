package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.services.impl.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByOrderId(@PathVariable("orderId") Long orderId) {
        List<OrderDetailDTO> orderDetailDTOs = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderDetailDTOs);
    }
}
