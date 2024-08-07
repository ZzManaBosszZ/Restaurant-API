package com.restaurant.restaurantapi.dtos.food;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class FoodDTO {
    private Long id;
    private String name;
    private String image;
    private Double price;
    private String description;
    private Integer quantity;
    private Double star;
    private Long categoryId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
