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
                .description(menu.getDescription())
                .menuFoodIds(menu.getMenuFoods().stream().map(mf -> mf.getId()).toList())
                .orderTableIds(menu.getOrderTables().stream().map(ot -> ot.getId()).toList())
                .build();
    }


}
