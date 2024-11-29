package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class TotalOrderDTO {
    private Integer year;
    private Integer month;
    private Long totalOrders;
    private double percentageGrowth;
}