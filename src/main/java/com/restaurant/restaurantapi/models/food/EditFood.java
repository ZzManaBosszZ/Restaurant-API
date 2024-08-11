package com.restaurant.restaurantapi.models.food;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditFood {
    private Long id;
    private String name;
    private MultipartFile image;
    private Double price;
    private String description;
    private Integer quantity;
    private Long categoryId;
}
