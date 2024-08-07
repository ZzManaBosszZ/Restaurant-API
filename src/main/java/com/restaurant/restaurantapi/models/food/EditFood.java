package com.restaurant.restaurantapi.models.food;

import lombok.Data;

@Data
public class EditFood {
    private Long id;
    private String name;
    private String image;
    private Double price;
    private String description;
    private Integer quantity;
    private Long categoryId;
}
