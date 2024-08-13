package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.menufood.MenuFoodDTO;
import com.restaurant.restaurantapi.entities.MenuFood;
import org.springframework.stereotype.Component;

@Component
public class MenuFoodMapper {

    public MenuFoodDTO toMenuFoodDTO(MenuFood model) {
        if (model == null) {
            throw new IllegalArgumentException("MenuFood entity must not be null");
        }
        return MenuFoodDTO.builder()
                .id(model.getId())
                .menuId(model.getMenu() != null ? model.getMenu().getId() : null)
                .foodId(model.getFood() != null ? model.getFood().getId() : null)
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
    }
}
