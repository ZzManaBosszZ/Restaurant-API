package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.food.FoodOrderDetailDTO;
import com.restaurant.restaurantapi.entities.FoodOrderDetail;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class FoodOrderDetailMapper {

    private final FoodMapper foodMapper;

    public FoodOrderDetailMapper(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }

    public FoodOrderDetailDTO toFoodOrderDetailDTO(FoodOrderDetail model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        return FoodOrderDetailDTO.builder()
                .id(model.getId())
                .food(foodMapper.toFoodSummaryDTO(model.getFood()))
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .createdDate(model.getCreatedDate())
                .build();
    }
}
