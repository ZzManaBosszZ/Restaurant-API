package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveredOrderDTO {
    private Integer year;
    private Integer month;
    private Long totalOrders; // Số đơn hàng đã giao
    private double percentageGrowth; // Phần trăm tăng trưởng
}