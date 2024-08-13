package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Orders findByOrderCode(String orderCode);
    List<Orders> findByUser(User user);
}
