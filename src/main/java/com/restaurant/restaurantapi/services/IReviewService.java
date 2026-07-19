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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IReviewService implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO create(CreateReview model, User user) throws AppException {
        Food food = foodRepository.findById(model.getFoodId())
                .orElseThrow(() -> new AppException(ErrorCode.MENU_NOTFOUND));
        Review review = Review.builder()
                .rating(model.getRating())
                .message(model.getMessage())
                .user(user)
                .food(food)
                .createdBy(user.getUsername())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedBy(user.getUsername())
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();

        review = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(review);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            Optional<Review> reviewOptional = reviewRepository.findById(id)
                    ;
            if (reviewOptional.isPresent()) {
                reviewRepository.deleteById(id)
                ;
            } else {
                throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
            }
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
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toReviewDTO)
                .toList();
    }

    @Override
    public List<ReviewDTO> findAllByFoodId(Long foodId) {
        return reviewRepository.findAllByFoodId(foodId).stream()
                .map(reviewMapper::toReviewDTO)
                .toList();
    }

    @Override
    public List<ReviewDTO> findAllByFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        return reviewRepository.findAllByFood(food).stream()
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