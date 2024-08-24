package com.restaurant.restaurantapi.dtos.food;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodSummaryDTO {
    private String name;
    private String image;
    private Double price;
}
