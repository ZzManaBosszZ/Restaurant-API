package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserSummaryDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .fullName(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .birthday(user.getBirthDay())
                .build();
    }
}
