package com.restaurant.restaurantapi.dtos.orders;

import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
@Builder
public class OrdersUserDTO {
    private Long id;
    private String orderCode;
    private BigDecimal total;
    private boolean isPaid;
    private OrderStatus status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
