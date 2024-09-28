package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.feedback.FeedbackDTO;
import com.restaurant.restaurantapi.models.feedback.CreateFeedback;
import com.restaurant.restaurantapi.services.impl.FeedbackService;
import com.restaurant.restaurantapi.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<ResponseObject> findAllFeedbacks() {
        List<FeedbackDTO> feedbacks = feedbackService.findAll();
        return ResponseEntity.ok(
                new ResponseObject(true, 200, "Fetch Success", feedbacks)
        );
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseObject> findFeedbackByEmail(@PathVariable String email) {
        FeedbackDTO feedback = feedbackService.findByEmail(email);
        return ResponseEntity.ok(
                new ResponseObject(true, 200, "Fetch Success", feedback)
        );
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createFeedback(@RequestBody CreateFeedback createFeedback) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject(false, 401, "User not authenticated", null)
            );
        }
        FeedbackDTO feedbackDTO = feedbackService.create(createFeedback, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(true, 201, "Create Success", feedbackDTO)
        );
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteFeedbacks(@RequestParam("ids") Long[] ids) {
        feedbackService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseObject(true, 204, "Delete Success", null)
        );
    }
}
