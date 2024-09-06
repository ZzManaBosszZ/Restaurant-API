package com.restaurant.restaurantapi.dtos.food;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FoodOrderDetailDTO {
    private Long id;
    private FoodSummaryDTO food;
    private Integer quantity;
    private BigDecimal unitPrice;
}
