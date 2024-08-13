package com.restaurant.restaurantapi.models.menufood;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditMenuFood {
    private Long id;
    private Long menuId;
    private Long foodId;
}
