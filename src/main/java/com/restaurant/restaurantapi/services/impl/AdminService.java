package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.menuadmin.Menu;
import com.restaurant.restaurantapi.dtos.orders.*;
import com.restaurant.restaurantapi.entities.User;

import java.util.List;

public interface AdminService {
    List<Menu> getMenu(User currenUser);
    TotalOrderDTO getTotalOrders(User currentUser);
    DeliveredOrderDTO getDeliveredOrders(User currentUser);
    CancelledOrderDTO getCancelledOrders(User currentUser);
    TotalRevenueDTO getTotalRevenue(User currentUser);
    DailyRevenueDTO getDailyRevenue(User currentUser);

}