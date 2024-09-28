package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.menufood.MenuFoodDTO;
import com.restaurant.restaurantapi.entities.MenuFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Component
public class MenuFoodMapper {
    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private MenuMapper menuMapper;

    public MenuFoodDTO toMenuFoodDTO(MenuFood model) {
        if (model == null) {
            throw new IllegalArgumentException("MenuFood entity must not be null");
        }
        return MenuFoodDTO.builder()
                .id(model.getId())
                .food(model.getFoods().stream()
                        .map(foodMapper::toFoodDTO)
                        .collect(Collectors.toList()))
                .menu(menuMapper.toMenuDTO(model.getMenu()))
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
    }
}
