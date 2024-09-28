package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.feedback.FeedbackDTO;
import com.restaurant.restaurantapi.entities.Feedback;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackDTO toFeedbackDTO(Feedback feedback) {
        if (feedback == null) throw new AppException(ErrorCode.FEEDBACK_NOTFOUND);
        return FeedbackDTO.builder()
                .id(feedback.getId())
                .name(feedback.getName())
                .email(feedback.getEmail())
                .phone(feedback.getPhone())
                .message(feedback.getMessage())
                .createdBy(feedback.getCreatedBy())
                .createdDate(feedback.getCreatedDate())
                .modifiedBy(feedback.getModifiedBy())
                .modifiedDate(feedback.getModifiedDate())
                .build();
    }

    // Có thể cần thêm các phương thức khác nếu cần thiết
}

