package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.entities.OrderStatus;
import com.restaurant.restaurantapi.models.orders.CreateOrders;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.services.impl.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/orders")
    public ResponseEntity<ResponseObject> getAll() {
        List<OrdersDTO> list = ordersService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") Long id) {
        OrdersDTO ordersDTO = ordersService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", ordersDTO)
        );
    }


    @GetMapping("/history")
    public ResponseEntity<ResponseObject> getOrderHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<OrdersDTO> orderHistory = ordersService.findOrdersByUser(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", orderHistory)
        );
    }
    @GetMapping("/{orderId}/history-detail")
    public ResponseEntity<ResponseObject> getOrderHistoryDetail(@PathVariable Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        OrderDetailDTO orderDetail = ordersService.getOrderDetailByIdAndUser(orderId, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok",orderDetail)
        );
    }
    @PostMapping("/orders")
    public ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateOrders createOrders) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject(false, 401, "User not authenticated", null)
            );
        }
        OrdersDTO ordersDTO = ordersService.create(createOrders, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(true, 201, "Create Success", ordersDTO)
        );
    }
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        ordersService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseObject(true, 204, "Delete Success", "")
        );
    }

    @PatchMapping("/order/{id}/status")
    public ResponseEntity<ResponseObject> updateOrderStatus(
            @PathVariable("id") Long orderId,
            @RequestParam("status") OrderStatus newStatus) {
        ordersService.updateStatus(orderId, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok","")
        );
    }

}
