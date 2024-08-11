package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDTO> getOrderDetailsByOrderId(Long orderId);
}
