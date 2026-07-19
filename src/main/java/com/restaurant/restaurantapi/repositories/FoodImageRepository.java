package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
}
