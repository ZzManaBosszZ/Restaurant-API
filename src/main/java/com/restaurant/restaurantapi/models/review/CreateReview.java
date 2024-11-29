package com.restaurant.restaurantapi.models.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReview {

    private Long foodId;

    @NotNull(message = "Rating is mandatory")
    private Double rating;

    private String message;
}
