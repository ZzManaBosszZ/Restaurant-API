package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.orderdetail.CreateOrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetailDTO findByOrderId(Long orderId, User user);
    OrderDetailDTO findById(Long id);
//    OrderDetailDTO createOrderDetail(Long orderId, CreateOrderDetail createOrderDetail);

}
