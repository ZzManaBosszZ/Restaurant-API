package com.restaurant.restaurantapi.dtos.orders;

import lombok.*;

import java.math.BigDecimal;
import java.time.Month;

@Setter
@Getter
public class TotalRevenueDTO {
    // Getters và Setters
    private int year;
    private int month;
    private BigDecimal totalRevenue;

    public TotalRevenueDTO(int year, int month,BigDecimal totalRevenue) {
        this.year = year;
        this.totalRevenue = totalRevenue;
        this.month = month;
    }

    @Override
    public String toString() {
        return "TotalRevenueDTO{" +
                "year=" + year +
                ", month=" + month +
                ", totalRevenue=" + totalRevenue +
                '}';
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}