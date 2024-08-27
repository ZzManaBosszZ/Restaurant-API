package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Cart;
import com.restaurant.restaurantapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
