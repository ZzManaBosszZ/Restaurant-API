package com.restaurant.restaurantapi.dtos.orderdetail;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderDetailDTO {
    private Long id;
    private Long orderId;
    private Long foodId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private Date createdDate;
    private Date modifiedDate;
}
