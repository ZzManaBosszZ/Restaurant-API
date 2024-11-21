package com.restaurant.restaurantapi.mappers;
import com.restaurant.restaurantapi.entities.MenuFood;
import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.entities.Menu;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {
    public MenuDTO toMenuDTO(Menu menu) {
        if (menu == null) {
            return null;
        }
        List<Long> menuFoodIds = menu.getMenuFoods().stream()
                .map(MenuFood::getId)
                .collect(Collectors.toList());
        return MenuDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .image(menu.getImage())
                .menuFoodIds(menuFoodIds)
                .description(menu.getDescription())
                .createdBy(menu.getCreatedBy())
                .createdDate(menu.getCreatedDate())
                .modifiedBy(menu.getModifiedBy())
                .modifiedDate(menu.getModifiedDate())
                .build();
    }

}