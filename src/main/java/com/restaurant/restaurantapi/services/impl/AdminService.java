package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.dtos.menuadmin.Menu;
import com.restaurant.restaurantapi.dtos.orders.*;
import com.restaurant.restaurantapi.entities.User;

import java.util.List;

public interface AdminService {
    List<Menu> getMenu(User currenUser);
    List<TotalOrderDTO> getTotalOrdersLast12Months(User currentUser);
    List<DeliveredOrderDTO> getDeliveredOrdersRevenueLast12Months(User currentUser);
    List<CancelledOrderDTO> getCancelledOrdersRevenueLast12Months(User currentUser);
    List<TotalRevenueDTO> getTotalMonthlyRevenueLast12Months(User currentUser);
    List<DailyRevenueDTO> getDailyRevenue(User currentUser);
    List<UserDTO> getUser(User currenUser);
    UserOrdersResponseDTO getOrdersByUser(Long userId, User currenUser);
    List<OrdersDTO> getOrdersReadyForShipping(User currentUser);
}