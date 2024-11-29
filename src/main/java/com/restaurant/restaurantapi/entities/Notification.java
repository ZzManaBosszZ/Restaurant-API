package com.restaurant.restaurantapi.entities;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
=======
import jakarta.persistence.*;
>>>>>>> main
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
@EqualsAndHashCode(callSuper = true)
@Table(name = "notifications")
@SuperBuilder
public class Notification extends BaseEntity{
=======
@Table(name = "notifications")
@SuperBuilder
public class Notification{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
>>>>>>> main

    private String message;
    private LocalDateTime timestamp;

<<<<<<< HEAD
=======
    private boolean isRead = false;  // mặc định là chưa đọc

    // Getters và setters

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }
>>>>>>> main
}

