package com.restaurant.restaurantapi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "carts")
@SuperBuilder
public class Cart extends BaseEntity {

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
