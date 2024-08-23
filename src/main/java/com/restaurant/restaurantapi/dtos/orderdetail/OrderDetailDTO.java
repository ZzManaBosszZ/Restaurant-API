package com.restaurant.restaurantapi.dtos.orderdetail;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@Builder
public class OrderDetailDTO {
    private Long id;
    private Long orderId;
    private Long foodId;
    private Integer quantity;
    private double unitPrice;
    private BigDecimal discount;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
