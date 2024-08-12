package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByFood(Food food);
}
