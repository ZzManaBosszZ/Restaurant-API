package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.Payment;
import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByOrderAndUser(Orders order, User user);
}
