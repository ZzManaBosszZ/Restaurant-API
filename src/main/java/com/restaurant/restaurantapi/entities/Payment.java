package com.restaurant.restaurantapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

//    @Column(name = "payment_date", nullable = false)
//    private LocalDateTime paymentDate;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
