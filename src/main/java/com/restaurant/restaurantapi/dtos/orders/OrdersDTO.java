package com.restaurant.restaurantapi.dtos.orders;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.OrderIsPaid;
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
    private OrderIsPaid isPaid;
    private Integer status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
