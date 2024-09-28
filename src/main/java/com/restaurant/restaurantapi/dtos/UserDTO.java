package com.restaurant.restaurantapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String code;

    private String fullName;

    private String email;

    private String phone;

    private Integer status;
    private String address;
    private Date birthday;
}
