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

    @Column(
            name = "paypal_order_id",
            unique = true,
            length = 100
    )
    private String paypalOrderId;

    @Column(
            name = "paypal_request_id",
            length = 100
    )
    private String paypalRequestId;

    @Column(
            name = "currency",
            nullable = false,
            length = 10
    )
    private String currency;

    @Column(
            name = "paypal_capture_id",
            unique = true,
            length = 100
    )
    private String paypalCaptureId;

    @Column(
            name = "capture_request_id",
            length = 100
    )
    private String captureRequestId;

    @Column(
            name = "failure_reason",
            length = 500
    )
    private String failureReason;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
