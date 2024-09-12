package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DailyRevenueDTO {
    private LocalDate date;
    private double totalRevenue;
    private double percentageGrowth;
}
