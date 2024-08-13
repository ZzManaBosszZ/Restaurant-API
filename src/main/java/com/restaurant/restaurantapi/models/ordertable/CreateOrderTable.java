package com.restaurant.restaurantapi.models.ordertable;

import lombok.Data;

import java.sql.Time;
import java.util.Date;
@Data
public class CreateOrderTable {
    private String name;
    private Integer numberOfPerson;
    private String email;
    private String phone;
    private Time time;
    private Date date;
    private Long menuId;
}
