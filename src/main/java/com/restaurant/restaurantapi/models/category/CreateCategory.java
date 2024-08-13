package com.restaurant.restaurantapi.models.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategory {
    @NotBlank(message = "Name is mandatory")
    private String name;
}
