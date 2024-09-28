package com.restaurant.restaurantapi.models.menu;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateMenu {
    @Size(max = 255)
    private String name;

    private MultipartFile image;

    @Size(max = 255)
    private String description;
}
