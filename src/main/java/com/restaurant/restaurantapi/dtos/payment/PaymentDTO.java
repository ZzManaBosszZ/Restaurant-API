package com.restaurant.restaurantapi.dtos.payment;

import com.restaurant.restaurantapi.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private Long userId;
    private Long orderId;
    private BigDecimal price;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private PaymentStatus status;
    private boolean isPaid;
    private String paypalOrderId;
    private String currency;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
