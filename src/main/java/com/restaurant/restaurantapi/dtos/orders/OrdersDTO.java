package com.restaurant.restaurantapi.dtos.orders;


import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class OrdersDTO {
    private Long id;
    private String orderCode;
    private BigDecimal total;
    private boolean isPaid;
    private OrderStatus status;
    private OrderDetailDTO orderDetail;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}