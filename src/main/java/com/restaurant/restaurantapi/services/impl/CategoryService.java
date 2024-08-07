package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.category.CategoryDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.category.CreateCategory;
import com.restaurant.restaurantapi.models.category.EditCategory;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
    CategoryDTO findBySlug(String slug);
    CategoryDTO create(CreateCategory createCategory, User user);
    CategoryDTO update(EditCategory editCategory, User user);
    void delete(Long[] ids);
}
