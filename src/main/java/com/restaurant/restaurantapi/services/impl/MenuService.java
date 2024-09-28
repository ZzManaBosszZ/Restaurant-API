package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.menu.CreateMenu;
import com.restaurant.restaurantapi.models.menu.EditMenu;

import java.util.List;

public interface MenuService {
    MenuDTO create(CreateMenu createMenu, User user);
    MenuDTO update(EditMenu editMenu, User user);
    void delete(Long[] id);
    MenuDTO findById(Long id);
    List<MenuDTO> findAll();
}