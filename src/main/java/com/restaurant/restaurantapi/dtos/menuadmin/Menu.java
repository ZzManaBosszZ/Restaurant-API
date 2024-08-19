package com.restaurant.restaurantapi.dtos.menuadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    private String label;
    private List<MenuItem> items;
}