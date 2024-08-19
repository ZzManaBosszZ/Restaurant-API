package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.entities.Menu;
import com.restaurant.restaurantapi.models.menu.CreateMenu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {
    public MenuDTO toMenuDTO(Menu menu) {
        if (menu == null) {
            return null;
        }
        return MenuDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .image(menu.getImage())
                .description(menu.getDescription())
                .createdBy(menu.getCreatedBy())
                .createdDate(menu.getCreatedDate())
                .modifiedBy(menu.getModifiedBy())
                .modifiedDate(menu.getModifiedDate())
                .build();
    }


}
