package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.menufood.MenuFoodDTO;
import com.restaurant.restaurantapi.entities.MenuFood;
import org.springframework.stereotype.Component;
import com.restaurant.restaurantapi.entities.Food;

import java.util.stream.Collectors;

@Component
public class MenuFoodMapper {

    public MenuFoodDTO toMenuFoodDTO(MenuFood model) {
        if (model == null) {
            throw new IllegalArgumentException("MenuFood entity must not be null");
        }
        return MenuFoodDTO.builder()
                .id(model.getId())
                .menuId(model.getMenu().getId())
                .foodId(model.getFood().stream()
                        .map(Food::getId)
                        .collect(Collectors.toList()))
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
    }
}
