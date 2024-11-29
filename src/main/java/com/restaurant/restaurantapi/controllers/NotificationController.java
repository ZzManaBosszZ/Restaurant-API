package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.entities.Notification;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.repositories.NotificationRepository;
import com.restaurant.restaurantapi.services.impl.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<Notification> notifications = notificationService.getAllNotifications(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", notifications)
        );
    }

    @PostMapping("/markAsRead/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        boolean isUpdated = notificationService.markAsRead(id);
        if (isUpdated) {
            return ResponseEntity.ok("Notification marked as read");
        } else {
            return ResponseEntity.badRequest().body("Notification not found or already read");
        }
    }
}
