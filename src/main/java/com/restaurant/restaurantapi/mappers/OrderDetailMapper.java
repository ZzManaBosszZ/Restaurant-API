package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    private final FoodMapper foodMapper;
    private final UserMapper userMapper;

    public OrderDetailMapper(FoodMapper foodMapper, UserMapper userMapper) {
        this.foodMapper = foodMapper;
        this.userMapper = userMapper;
    }

    public OrderDetailDTO toOrderDetailDTO(OrderDetail model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        return OrderDetailDTO.builder()
                .id(model.getId())
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .discount(model.getDiscount())
                .createdDate(model.getCreatedDate())
                .modifiedDate(model.getModifiedDate())
                .createdBy(model.getCreatedBy())
                .modifiedBy(model.getModifiedBy())
                .food(foodMapper.toFoodSummaryDTO(model.getFood()))
                .user(userMapper.toUserSummaryDTO(model.getUser()))
                .build();
    }
}
