package com.restaurant.restaurantapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
@SuperBuilder
public class Food extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoodImage> images = new ArrayList<>();

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "star")
    private Double star;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @JsonIgnore
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews ;

    @JsonIgnore
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Wishlist> wishlists ;

    @JsonIgnore
    @ManyToMany(mappedBy = "foods", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MenuFood> menuFoods;
}
