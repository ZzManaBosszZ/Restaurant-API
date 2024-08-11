package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.feedback.FeedbackDTO;
import com.restaurant.restaurantapi.entities.Feedback;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.FeedbackMapper;
import com.restaurant.restaurantapi.models.feedback.CreateFeedback;
import com.restaurant.restaurantapi.repositories.FeedbackRepository;
import com.restaurant.restaurantapi.services.impl.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IFeedbackService implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public List<FeedbackDTO> findAll() {
        return feedbackRepository.findAll().stream()
                .map(feedbackMapper::toFeedbackDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackDTO findByEmail(String email) {
        Feedback feedback = feedbackRepository.findByEmail(email);
        if (feedback == null) throw new AppException(ErrorCode.FEEDBACK_NOTFOUND);
        return feedbackMapper.toFeedbackDTO(feedback);
    }

    @Override
    public FeedbackDTO create(CreateFeedback createFeedback, User user) {
        Feedback existingFeedback = feedbackRepository.findByEmail(createFeedback.getEmail());
        if (existingFeedback != null) throw new AppException(ErrorCode.FEEDBACK_EXISTED);
        Feedback feedback = Feedback.builder()
                .name(createFeedback.getName())
                .email(createFeedback.getEmail())
                .phone(createFeedback.getPhone())
                .message(createFeedback.getMessage())
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();

        feedbackRepository.save(feedback);
        return feedbackMapper.toFeedbackDTO(feedback);
    }

//    @Override
//    public FeedbackDTO update(EditFeedback editFeedback, User user) {
//        Feedback existingFeedback = feedbackRepository.findById(editFeedback.getId())
//                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOTFOUND));
//
//        Feedback feedbackWithSameEmail = feedbackRepository.findByEmail(editFeedback.getEmail());
//        if (feedbackWithSameEmail != null && !feedbackWithSameEmail.getId().equals(editFeedback.getId())) {
//            throw new AppException(ErrorCode.FEEDBACK_EXISTED);
//        }
//
//        existingFeedback.setName(editFeedback.getName());
//        existingFeedback.setEmail(editFeedback.getEmail());
//        existingFeedback.setPhone(editFeedback.getPhone());
//        existingFeedback.setMessage(editFeedback.getMessage());
//        existingFeedback.setModifiedBy(user.getFullName());
//        existingFeedback.setModifiedDate(new Timestamp(System.currentTimeMillis()));
//
//        feedbackRepository.save(existingFeedback);
//        return feedbackMapper.toFeedbackDTO(existingFeedback);
//    }

    @Override
    public void delete(Long[] ids) {
        feedbackRepository.deleteAllById(List.of(ids));
    }
}
