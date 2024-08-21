package com.restaurant.restaurantapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@Builder
public class Payment extends BaseEntity {

    @Column(name = "paymentMethod", nullable = false)
    private String paymentMethod;

    @Column(name = "isPaid", nullable = false)
    private boolean isPaid;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "paymentDate", nullable = false)
    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}