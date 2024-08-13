package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.models.menu.CreateMenu;
import com.restaurant.restaurantapi.models.menu.EditMenu;

import java.util.List;

public interface MenuService {
    MenuDTO create(CreateMenu createMenu);
    MenuDTO update(EditMenu editMenu);
    void delete(Long id);
    MenuDTO findById(Long id);
    List<MenuDTO> findAll();
}
