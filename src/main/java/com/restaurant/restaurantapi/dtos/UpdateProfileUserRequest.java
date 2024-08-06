package com.restaurant.restaurantapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileUserRequest {
    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dob;
    @NotEmpty(message = "Phone number cannot be empty")
    private String phone;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
}