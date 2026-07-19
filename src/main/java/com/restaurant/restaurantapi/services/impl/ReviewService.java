    package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.review.ReviewDTO;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.review.CreateReview;

    import java.util.List;

public interface ReviewService {
    ReviewDTO create(CreateReview model, User user);
    //    ReviewDTO update(EditReview model, Long userId);
    void delete(Long[] ids);
    ReviewDTO findById(Long id);
    List<ReviewDTO> findAll();
    List<ReviewDTO> findAllByFoodId(Long foodId);
    List<ReviewDTO> findAllByFood(Long foodId);
    ReviewDTO findByFoodAndUser(Long foodId, Long userId);
}
