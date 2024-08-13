package com.restaurant.restaurantapi.mappers;
import com.restaurant.restaurantapi.entities.Review;
import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.entities.Food;
import org.springframework.stereotype.Component;

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

        return FoodDTO.builder()
                .id(food.getId())
                .name(food.getName())
                .image(food.getImage())
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
}