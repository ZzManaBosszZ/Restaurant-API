package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByEmail(String email);

}
