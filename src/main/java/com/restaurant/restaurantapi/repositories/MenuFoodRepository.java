package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.MenuFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuFoodRepository extends JpaRepository<MenuFood, Long> {
}
