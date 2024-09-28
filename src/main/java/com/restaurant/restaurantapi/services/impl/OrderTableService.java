package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.ordertable.OrderTableDTO;
import com.restaurant.restaurantapi.models.ordertable.CreateOrderTable;

import java.util.List;

public interface OrderTableService {
    OrderTableDTO createOrderTable(CreateOrderTable createOrderTable);

    OrderTableDTO getOrderTableById(Long id);

    List<OrderTableDTO> getAllOrderTables();

//    OrderTableDTO updateOrderTable(Long id, CreateOrderTable updateOrderTable);

    void deleteOrderTable(Long id);
    OrderTableDTO acceptOrderTable(Long id);
}
