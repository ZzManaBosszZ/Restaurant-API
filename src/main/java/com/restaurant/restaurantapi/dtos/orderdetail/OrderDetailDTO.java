package com.restaurant.restaurantapi.dtos.orderdetail;

import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.dtos.food.FoodOrderDetailDTO;
import com.restaurant.restaurantapi.dtos.food.FoodSummaryDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Data
@Builder
public class OrderDetailDTO {
    private Long id;
//    private Integer quantity;
//    private double unitPrice;
    private BigDecimal discount;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private List<FoodOrderDetailDTO> foodOrderDetails;
    private UserDTO user;
}
