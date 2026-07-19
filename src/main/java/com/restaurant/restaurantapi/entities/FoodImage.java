package com.restaurant.restaurantapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Table(name = "food_image")
public class FoodImage extends BaseEntity {

    @Column(name = "image_url", nullable = false)
    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL should be a valid URL")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
