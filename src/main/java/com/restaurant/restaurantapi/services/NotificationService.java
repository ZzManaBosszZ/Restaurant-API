package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.entities.Notification;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.repositories.NotificationRepository;
import com.restaurant.restaurantapi.services.impl.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotifications(User user) {
        return notificationRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    public void createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
//        notification.getCreatedBy();
        notificationRepository.save(notification);
    }

    @Transactional
    public boolean markAsRead(Long id) {
        return notificationRepository.findById(id).map(notification -> {
            if (!notification.isRead()) {
                notification.setRead(true);
                notificationRepository.save(notification);
                return true;
            }
            return false;
        }).orElse(false);
    }
}

