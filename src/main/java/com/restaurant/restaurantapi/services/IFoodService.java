package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.entities.Category;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.FoodMapper;
import com.restaurant.restaurantapi.models.food.CreateFood;
import com.restaurant.restaurantapi.models.food.EditFood;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.services.impl.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IFoodService implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    @Override
    public FoodDTO create(CreateFood createFood, User user) {
        Food existingFood = foodRepository.findByNameAndCategoryId(createFood.getName(), createFood.getCategoryId());
        if (existingFood != null) {
            throw new AppException(ErrorCode.FOOD_EXISTED);
        }
        Food food = Food.builder()
                .name(createFood.getName())
                .image(createFood.getImage())
                .price(createFood.getPrice())
                .description(createFood.getDescription())
                .quantity(createFood.getQuantity())
                .star(0.0)
                .createdBy(user.getUsername())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedBy(user.getUsername())
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();

        food = foodRepository.save(food);
        return foodMapper.toFoodDTO(food);
    }

    @Override
    public FoodDTO update(EditFood editFood, User user) {
        Optional<Food> foodOptional = foodRepository.findById(editFood.getId());
        if (!foodOptional.isPresent()) {
            throw new AppException(ErrorCode.FOOD_NOTFOUND);
        }
        Food food = foodOptional.get();
        food.setName(editFood.getName());
        food.setImage(editFood.getImage());
        food.setPrice(editFood.getPrice());
        food.setDescription(editFood.getDescription());
        food.setQuantity(editFood.getQuantity());
        food.setModifiedBy(user.getUsername());
        food.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        food = foodRepository.save(food);
        return foodMapper.toFoodDTO(food);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            foodRepository.deleteById(id);
        }
    }

    @Override
    public FoodDTO findById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        return foodMapper.toFoodDTO(food);
    }

    @Override
    public List<FoodDTO> findAll() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }

    @Override
    public List<FoodDTO> findAllByCategoryId(Long categoryId) {
        List<Food> foods = foodRepository.findAllByCategoryId(categoryId);
        return foods.stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }

    @Override
    public List<FoodDTO> findAllByPriceGreaterThan(Double price) {
        List<Food> foods = foodRepository.findAllByPriceGreaterThan(price);
        return foods.stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }

    @Override
    public List<FoodDTO> findAllByPriceLessThan(Double price) {
        List<Food> foods = foodRepository.findAllByPriceLessThan(price);
        return foods.stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }
}
