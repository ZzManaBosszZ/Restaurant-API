package com.restaurant.restaurantapi.models.menu;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class CreateMenu {
    @NotEmpty
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;
}
