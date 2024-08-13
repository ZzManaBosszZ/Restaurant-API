package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.models.orderdetail.CreateOrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDTO> findByOrderId(Long orderId);
    OrderDetailDTO findById(Long id);
    OrderDetailDTO createOrderDetail(Long orderId, CreateOrderDetail createOrderDetail);
}