package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelledOrderDTO {
    private int year;
    private int month;
    private long totalOrders;
    private double percentageGrowth;; // Phần trăm tăng trưởng
}
