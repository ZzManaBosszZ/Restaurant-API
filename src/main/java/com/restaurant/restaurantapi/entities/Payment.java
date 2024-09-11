package com.restaurant.restaurantapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@Builder
public class Payment extends BaseEntity {

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
