package com.restaurant.restaurantapi.models.orders;

import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.models.orderdetail.CreateOrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrders {
    private String orderCode;
    private BigDecimal total;
    private Boolean isPaid;
    private Integer status;
    private List<CreateOrderDetail> orderDetails;
}
