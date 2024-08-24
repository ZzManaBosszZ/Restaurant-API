package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalOrderDTO {
    private Long totalOrders;
    private double percentageGrowth;
}
