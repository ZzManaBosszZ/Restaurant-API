package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    public OrderDetailDTO toOrderDetailDTO(OrderDetail model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        return OrderDetailDTO.builder()
                .id(model.getId())
                .orderId(model.getOrder() != null ? model.getOrder().getId() : null)
                .foodId(model.getFood() != null ? model.getFood().getId() : null)
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .discount(model.getDiscount())
                .createdDate(model.getCreatedDate())
                .modifiedDate(model.getModifiedDate())
                .build();
    }
}
