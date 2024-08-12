package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.feedback.FeedbackDTO;

import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.feedback.CreateFeedback;


import java.util.List;

public interface FeedbackService {
    List<FeedbackDTO> findAll();
    FeedbackDTO findByEmail(String email);
    FeedbackDTO create(CreateFeedback createFeedback, User user);
    void delete(Long[] ids);
}
