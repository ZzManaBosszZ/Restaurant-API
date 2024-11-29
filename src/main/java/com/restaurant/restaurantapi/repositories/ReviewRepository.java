package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.Review;
import com.restaurant.restaurantapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByFood(Food food);
    Review findByFoodAndUser(Food food, User user);
    List<Review> findAllByFoodId(Long foodId);
}
