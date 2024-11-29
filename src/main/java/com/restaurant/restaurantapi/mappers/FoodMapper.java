package com.restaurant.restaurantapi.mappers;
import com.restaurant.restaurantapi.dtos.food.FoodSummaryDTO;
import com.restaurant.restaurantapi.entities.Review;
import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.entities.Food;
import org.springframework.stereotype.Component;
import com.restaurant.restaurantapi.entities.FoodImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodMapper {
    public FoodDTO toFoodDTO(Food food) {
        if (food == null) {
            throw new RuntimeException("Food entity not found");
        }

        Double averageRating = food.getReviews().stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        List<String> foodImages = food.getImages() != null ? food.getImages().stream()
                .map(FoodImage::getImageUrl)
                .collect(Collectors.toList()) : new ArrayList<>();
        return FoodDTO.builder()
                .id(food.getId())
                .name(food.getName())
                .image(foodImages)
                .price(food.getPrice())
                .description(food.getDescription())
                .quantity(food.getQuantity())
                .star(averageRating)
                .categoryId(food.getCategory().getId())
                .createdDate(food.getCreatedDate())
                .modifiedDate(food.getModifiedDate())
                .createdBy(food.getCreatedBy())
                .modifiedBy(food.getModifiedBy())
                .build();
    }

    public FoodSummaryDTO toFoodSummaryDTO(Food food) {
        if (food == null) {
            return null;
        }
        String foodImage = food.getImages().stream()
                .map(FoodImage::getImageUrl)
                .findFirst()
                .orElse(null);
        return FoodSummaryDTO.builder()
                .name(food.getName())
                .image(foodImage)
                .price(food.getPrice())
                .build();
    }
}