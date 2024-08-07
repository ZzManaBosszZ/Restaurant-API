package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.review.ReviewDTO;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.Review;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.ReviewMapper;
import com.restaurant.restaurantapi.models.review.CreateReview;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.repositories.ReviewRepository;
import com.restaurant.restaurantapi.repositories.UserRepository;

import com.restaurant.restaurantapi.services.impl.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IReviewService implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO create(CreateReview model, Long userId, Long foodId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));

        Review existingReview = reviewRepository.findByFoodAndUser(food, user);
        if (existingReview != null) {
            throw new AppException(ErrorCode.REVIEW_EXISTED);
        }

        Review review = Review.builder()
                .rating(model.getRating())
                .message(model.getMessage())
                .user(user)
                .food(food)
//                .createdBy(user.getUsername())
//                .createdDate(new Timestamp(System.currentTimeMillis()))
//                .modifiedBy(user.getUsername())
//                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();

        review = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(review);
    }

//    @Override
//    public ReviewDTO update(EditReview model, Long userId) {
//        Review review = reviewRepository.findById(model.getId())
//                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        review.setRating(model.getRating());
//        review.setMessage(model.getMessage());
//        review.setModifiedBy(user.getUsername());
//        review.setModifiedDate(new Timestamp(System.currentTimeMillis()));
//
//        review = reviewRepository.save(review);
//        return reviewMapper.toReviewDTO(review);
//    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            reviewRepository.deleteById(id);
        }
    }

    @Override
    public ReviewDTO findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return reviewMapper.toReviewDTO(review);
    }

    @Override
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(reviewMapper::toReviewDTO)
                .toList();
    }

    @Override
    public List<ReviewDTO> findAllByFoodId(Long foodId) {
        List<Review> reviews = reviewRepository.findAllByFoodId(foodId);
        return reviews.stream()
                .map(reviewMapper::toReviewDTO)
                .toList();
    }

    @Override
    public List<ReviewDTO> findAllByFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        List<Review> reviews = reviewRepository.findAllByFood(food);
        return reviews.stream()
                .map(reviewMapper::toReviewDTO)
                .toList();
    }

    @Override
    public ReviewDTO findByFoodAndUser(Long foodId, Long userId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Review review = reviewRepository.findByFoodAndUser(food, user);
        if (review == null) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        return reviewMapper.toReviewDTO(review);
    }
}
