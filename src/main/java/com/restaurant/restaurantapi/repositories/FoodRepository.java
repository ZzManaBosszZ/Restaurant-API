package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByCategoryId(Long categoryId);
    Food findByNameAndCategoryId(String name, Long categoryId);
    List<Food> findAllByPriceGreaterThan(Double price);
    List<Food> findAllByPriceLessThan(Double price);
    List<Food> findAllByUserId(Long userId);
}
