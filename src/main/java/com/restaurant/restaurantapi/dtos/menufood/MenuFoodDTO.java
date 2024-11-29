package com.restaurant.restaurantapi.dtos.menufood;

import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuFoodDTO {
    private Long id;
    private List<FoodDTO> food;
    private MenuDTO menu;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}