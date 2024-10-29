package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByOrderByTimestampDesc();
}
