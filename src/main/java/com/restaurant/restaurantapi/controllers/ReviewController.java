package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.review.ReviewDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.review.CreateReview;

import com.restaurant.restaurantapi.services.impl.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("review")
    ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateReview createReview) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        ReviewDTO reviewDTO = reviewService.create(createReview, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", reviewDTO)
        );
    }


    @DeleteMapping
    ResponseEntity<ResponseObject> delete(@RequestBody Long[] ids) {
        reviewService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Delete success", "")
        );
    }

//    @GetMapping("/{id}")
//    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
//        ReviewDTO reviewDTO = reviewService.findById(id)
//                ;
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(true, 200, "Find Success", reviewDTO)
//        );
//    }

    @GetMapping("any/review/food/{foodId}")
    ResponseEntity<ResponseObject> findAllByFoodId(@PathVariable Long foodId) {
        List<ReviewDTO> reviews = reviewService.findAllByFoodId(foodId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Find Success", reviews)
        );
    }

    @GetMapping("/user/{userId}/food/{foodId}")
    ResponseEntity<ResponseObject> findByFoodAndUser(@PathVariable Long userId, @PathVariable Long foodId) {
        ReviewDTO reviewDTO = reviewService.findByFoodAndUser(foodId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Find Success", reviewDTO)
        );
    }
}