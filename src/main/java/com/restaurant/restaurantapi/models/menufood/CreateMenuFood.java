package com.restaurant.restaurantapi.models.menufood;

import lombok.Data;

import java.util.List;

@Data
public class CreateMenuFood {
    private Long menuId;
    private List<Long> foodId;
}
