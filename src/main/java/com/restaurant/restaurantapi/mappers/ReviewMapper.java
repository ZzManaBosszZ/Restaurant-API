package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.dtos.review.ReviewDTO;
import com.restaurant.restaurantapi.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    private final UserMapper userMapper;

    public ReviewMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public ReviewDTO toReviewDTO(Review review) {
        if (review == null) {
            throw new RuntimeException("Review not found");
        }

        return ReviewDTO.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .foodId(review.getFood().getId())
                .rating(review.getRating())
                .message(review.getMessage())
                .createdBy(review.getCreatedBy())
                .createdDate(review.getCreatedDate())
                .modifiedBy(review.getModifiedBy())
                .modifiedDate(review.getModifiedDate())
                .user(userMapper.toUserSummaryDTO(review.getUser()))
                .build();
    }

}
