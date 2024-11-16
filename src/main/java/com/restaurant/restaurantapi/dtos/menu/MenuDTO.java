package com.restaurant.restaurantapi.dtos.menu;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class MenuDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private List<Long> menuFoodIds;
    private List<Long> orderTableIds;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}