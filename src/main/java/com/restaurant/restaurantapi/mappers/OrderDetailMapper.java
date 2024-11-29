package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderDetailMapper {


    private final UserMapper userMapper;
    private final FoodOrderDetailMapper foodOrderDetailMapper;
    private final FoodMapper foodMapper;

    public OrderDetailMapper(FoodMapper foodMapper, UserMapper userMapper, FoodOrderDetailMapper foodOrderDetailMapper) {
        this.foodMapper = foodMapper;

        this.userMapper = userMapper;
        this.foodOrderDetailMapper = foodOrderDetailMapper;
    }

    public OrderDetailDTO toOrderDetailDTO(OrderDetail model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        return OrderDetailDTO.builder()
                .id(model.getId())
                .discount(model.getDiscount())
                .createdDate(model.getCreatedDate())
                .modifiedDate(model.getModifiedDate())
                .createdBy(model.getCreatedBy())
                .modifiedBy(model.getModifiedBy())
                .foodOrderDetails(model.getFoodOrderDetails().stream()
                        .map(foodOrderDetailMapper::toFoodOrderDetailDTO)
                        .collect(Collectors.toList()))
                .user(userMapper.toUserSummaryDTO(model.getUser()))
                .build();
    }
}