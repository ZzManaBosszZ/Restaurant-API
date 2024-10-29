package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.entities.Notification;
import com.restaurant.restaurantapi.entities.User;

import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotifications(User user);
    void createNotification(String message);
    boolean markAsRead(Long notificationId);
}
