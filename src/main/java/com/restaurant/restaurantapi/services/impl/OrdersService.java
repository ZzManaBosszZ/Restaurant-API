package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.OrderStatus;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.models.orders.CreateOrders;


import java.util.List;

public interface OrdersService {
    OrdersDTO create(CreateOrders createOrders, User user);
    void delete(Long id);
    OrdersDTO findById(Long id);
    List<OrdersDTO> findAll();
    List<OrdersDTO> findOrdersByUser(User user);
    void updateStatus(Long orderId, OrderStatus newStatus) ;
    OrderDetailDTO getOrderDetailByIdAndUser(Long orderId, User currentUser);


}
