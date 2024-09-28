package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.ordertable.OrderTableDTO;
import com.restaurant.restaurantapi.entities.Menu;
import com.restaurant.restaurantapi.entities.OrderTable;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.repositories.MenuRepository;
import com.restaurant.restaurantapi.repositories.OrderTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTableMapper {
    @Autowired
    private OrderTableRepository orderTableRepository;
    @Autowired
    private MenuRepository menuRepository;
    public OrderTableDTO toOrderTableDTO(OrderTable model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);
        String menuName = model.getMenu() != null ? model.getMenu().getName() : "Unknown";
        return OrderTableDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .status(model.getStatus())
                .numberOfPerson(model.getNumberOfPerson())
                .menuName(model.getMenu().getName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .time(model.getTime())
                .date(model.getDate())
                .menuId(model.getMenu() != null ? model.getMenu().getId() : null)
                .menuName(menuName)
                .build();
    }
}
