package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.ordertable.OrderTableDTO;
import com.restaurant.restaurantapi.entities.OrderTable;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class OrderTableMapper {
    public OrderTableDTO toOrderTableDTO(OrderTable model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);
        return OrderTableDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .status(model.getStatus())
                .numberOfPerson(model.getNumberOfPerson())
                .email(model.getEmail())
                .phone(model.getPhone())
                .time(model.getTime())
                .date(model.getDate())
                .menuId(model.getMenu() != null ? model.getMenu().getId() : null)
                .build();
    }
}
