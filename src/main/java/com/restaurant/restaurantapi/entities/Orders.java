package com.restaurant.restaurantapi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@SuperBuilder
public class Orders extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_code", length = 255)
    private String orderCode;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
