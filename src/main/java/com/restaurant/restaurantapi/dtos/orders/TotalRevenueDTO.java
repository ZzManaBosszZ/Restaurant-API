package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalRevenueDTO {
    private Double totalRevenue;
    private double percentageGrowth; // Phần trăm tăng trưởng
}
