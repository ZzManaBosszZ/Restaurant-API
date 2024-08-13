package com.restaurant.restaurantapi.models.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCategory {
    @NotNull(message = "Id is mandatory")
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
}