package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrdersMapper {

    private OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return OrderDetailDTO.builder()
                .id(orderDetail.getId())
                .foodId(orderDetail.getFood().getId())
                .orderId(orderDetail.getOrder().getId())
                .quantity(orderDetail.getQuantity())
                .discount(orderDetail.getDiscount())
                .unitPrice(orderDetail.getUnitPrice())
                .createdDate(orderDetail.getCreatedDate())
                .modifiedDate(orderDetail.getModifiedDate())
                .build();
    }
    public OrdersDTO toOrdersDTO(Orders model) {
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        OrdersDTO orderDTO = OrdersDTO.builder()
                .id(model.getId())
                .orderCode(model.getOrderCode())
                .total(model.getTotal())
                .status(model.getStatus())
                .isPaid(model.isPaid())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .orderDetails(model.getOrderDetails().stream()
                        .map(this::toOrderDetailDTO)
                        .collect(Collectors.toList()))
                .build();
        return orderDTO;
    }
}
