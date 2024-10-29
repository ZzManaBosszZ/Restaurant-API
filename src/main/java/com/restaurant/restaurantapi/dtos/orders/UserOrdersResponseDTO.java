package com.restaurant.restaurantapi.dtos.orders;

import com.restaurant.restaurantapi.dtos.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserOrdersResponseDTO {
    private UserDTO user;
    private List<OrdersUserDTO> orders;
}

