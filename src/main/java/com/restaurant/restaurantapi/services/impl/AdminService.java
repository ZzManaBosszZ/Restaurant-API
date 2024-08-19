package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.menuadmin.Menu;
import com.restaurant.restaurantapi.entities.User;

import java.util.List;

public interface AdminService {
    List<Menu> getMenu(User currenUser);

}