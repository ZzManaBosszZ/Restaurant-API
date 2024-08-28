package com.restaurant.restaurantapi.dtos.ordertable;

import com.restaurant.restaurantapi.entities.OrderStatus;
import lombok.Builder;
import lombok.Data;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderTableDTO {
    private OrderStatus status;
    private Long id;
    private String name;
    private Integer numberOfPerson;
    private String email;
    private String phone;
    private Time time;
    private Date date;
    private Long menuId;
    private String menuName;
}
