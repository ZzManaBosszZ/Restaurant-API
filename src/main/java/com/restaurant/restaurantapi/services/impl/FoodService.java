package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.models.food.CreateFood;
import com.restaurant.restaurantapi.models.food.EditFood;
import com.restaurant.restaurantapi.entities.User;

import java.util.List;

public interface FoodService {
    FoodDTO create(CreateFood createFood, User user);
    FoodDTO update(EditFood editFood, User user);
    void delete(Long[] ids);
    FoodDTO findById(Long id);
    List<FoodDTO> findAll();
    List<FoodDTO> findAllByCategoryId(Long categoryId);
    List<FoodDTO> findAllByPriceGreaterThan(Double price);
    List<FoodDTO> findAllByPriceLessThan(Double price);
}
