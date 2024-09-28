package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveredOrderDTO {
    private Long deliveredOrders; // Số đơn hàng đã giao
    private double percentageGrowth; // Phần trăm tăng trưởng
}
