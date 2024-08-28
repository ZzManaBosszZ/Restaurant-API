package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.CartItem;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
