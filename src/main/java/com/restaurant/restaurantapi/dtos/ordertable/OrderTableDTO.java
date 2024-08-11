package com.restaurant.restaurantapi.dtos.ordertable;

import lombok.Builder;
import lombok.Data;
import java.sql.Time;
import java.util.Date;

@Data
@Builder
public class OrderTableDTO {
    private Long id;
    private String name;
    private Integer numberOfPerson;
    private String email;
    private String phone;
    private Time time;
    private Date date;
    private Long menuId;
}
