package com.restaurant.restaurantapi.dtos.food;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class FoodOrderDetailDTO {
    private Long id;
    private FoodSummaryDTO food;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Timestamp createdDate;
}
