package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrdersMapper {
//
    public OrdersDTO toOrdersDTO(Orders model) {
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        OrdersDTO orderDTO = OrdersDTO.builder()
                .id(model.getId())
                .orderCode(model.getOrderCode())
                .total(model.getTotal())
                .status(model.getStatus())
                .isPaid(model.getIsPaid())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return orderDTO;
    }
}
