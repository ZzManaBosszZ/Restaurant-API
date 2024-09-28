package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.food.FoodOrderDetailDTO;
import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdersMapper {

    private final OrderDetailMapper orderDetailMapper;

    // Constructor injection for OrderDetailMapper
    public OrdersMapper(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    public OrdersDTO toOrdersDTO(Orders model) {
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        OrderDetailDTO orderDetailDTO = model.getOrderDetail() != null
                ? orderDetailMapper.toOrderDetailDTO(model.getOrderDetail())
                : null;


        return OrdersDTO.builder()
                .id(model.getId())
                .orderCode(model.getOrderCode())
                .total(model.getTotal())
                .status(model.getStatus())
                .isPaid(model.isPaid())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .orderDetail(orderDetailDTO)
                .build();
    }
}
