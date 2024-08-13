package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.dtos.menufood.MenuFoodDTO;
import com.restaurant.restaurantapi.entities.User;

import com.restaurant.restaurantapi.models.menufood.CreateMenuFood;
import com.restaurant.restaurantapi.models.menufood.EditMenuFood;

import java.util.List;

public interface MenuFoodService {
    MenuFoodDTO create(CreateMenuFood createMenuFood, User user);
    MenuFoodDTO update(EditMenuFood editMenuFood , User user);
    void delete(Long[] ids);
    MenuFoodDTO findById(Long id);
    List<MenuFoodDTO> findAll();
}
