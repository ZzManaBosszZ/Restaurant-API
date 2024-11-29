package com.restaurant.restaurantapi.dtos.review;

import com.restaurant.restaurantapi.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long foodId;
    private Double rating;
    private String message;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private UserDTO user;
}
