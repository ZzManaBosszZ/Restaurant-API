package com.restaurant.restaurantapi.models.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReview {
    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotNull(message = "Food ID is mandatory")
    private Long foodId;

    @NotNull(message = "Rating is mandatory")
    private Double rating;

    private String message;
}
