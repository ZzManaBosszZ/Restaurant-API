package com.restaurant.restaurantapi.dtos.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyRevenueDTO {
    private double totalRevenueToday;
    private double percentageGrowthLastWeek;
}
