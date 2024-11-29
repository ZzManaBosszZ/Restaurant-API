package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.models.orders.CreateOrders;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;


import java.util.List;

public interface OrdersService {
//    OrdersDTO create(User user, HttpSession session);
    OrdersDTO create(CreateOrders createOrders, User user);
    void delete(Long id);
    OrdersDTO findById(Long id);
    List<OrdersDTO> findAll();
    List<OrdersDTO> findOrdersByUser(User user);

}
