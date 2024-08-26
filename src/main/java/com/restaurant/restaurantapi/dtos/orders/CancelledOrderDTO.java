package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelledOrderDTO {
    private Long cancelledOrders;
    private double percentageGrowth; // Phần trăm tăng trưởng
}
