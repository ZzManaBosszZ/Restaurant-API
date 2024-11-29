package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.entities.Category;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.FoodImage;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.FoodMapper;
import com.restaurant.restaurantapi.models.food.CreateFood;
import com.restaurant.restaurantapi.models.food.EditFood;
import com.restaurant.restaurantapi.repositories.CategoryRepository;
import com.restaurant.restaurantapi.repositories.FoodImageRepository;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.repositories.UserRepository;
import com.restaurant.restaurantapi.services.impl.FoodService;
import com.restaurant.restaurantapi.services.impl.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IFoodService implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final StorageService storageService;
    private final CategoryRepository categoryRepository;
    private final UserRepository UserRepository;
    private final FoodImageRepository foodImageRepository;

//    @Override
//    public FoodDTO create(CreateFood createFood, User user) {
//        Food existingFood = foodRepository.findByNameAndCategoryId(createFood.getName(), createFood.getCategoryId());
//        if (existingFood != null) {
//            throw new AppException(ErrorCode.FOOD_EXISTED);
//        }
//        Category category = categoryRepository.findById(createFood.getCategoryId())
//                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
//        String generatedFileName = storageService.storeFile(createFood.getImage());
//        Food food = Food.builder()
//                .name(createFood.getName())
//                .image("http://localhost:8080/api/v1/FileUpload/files/" + generatedFileName)
//                .price(createFood.getPrice())
//                .description(createFood.getDescription())
//                .quantity(createFood.getQuantity())
//                .star(0.0)
//                .createdBy(user.getUsername())
//                .category(category)
//                .user(user)
//                .reviews(new ArrayList<>())
//                .createdDate(new Timestamp(System.currentTimeMillis()))
//                .modifiedBy(user.getUsername())
//                .modifiedDate(new Timestamp(System.currentTimeMillis()))
//                .build();
//        food = foodRepository.save(food);
//        return foodMapper.toFoodDTO(food);
//    }
//
//    public FoodDTO update(EditFood editFood, User user) {
//        Food food = foodRepository.findById(editFood.getId())
//                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
//        String imageUrl = food.getImage();
//        if (editFood.getImage() != null && !editFood.getImage().isEmpty()) {
//            try {
//                String generatedFileName = storageService.storeFile(editFood.getImage());
//                imageUrl = "http://localhost:8080/api/v1/FileUpload/files/" + generatedFileName;
//            } catch (Exception e) {
//                throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
//            }
//        }
//        food.setName(editFood.getName());
//        food.setImage(imageUrl);
//        food.setPrice(editFood.getPrice());
//        food.setDescription(editFood.getDescription());
//        food.setQuantity(editFood.getQuantity());
//        food.setModifiedBy(user.getUsername());
//        food.setModifiedDate(new Timestamp(System.currentTimeMillis()));
//        food = foodRepository.save(food);
//        return foodMapper.toFoodDTO(food);
//    }

    @Override
    public FoodDTO create(CreateFood createFood, User user) {
        Food existingFood = foodRepository.findByNameAndCategoryId(createFood.getName(), createFood.getCategoryId());
        if (existingFood != null) {
            throw new AppException(ErrorCode.FOOD_EXISTED);
        }
        Category category = categoryRepository.findById(createFood.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        Food food = Food.builder()
                .name(createFood.getName())
                .price(createFood.getPrice())
                .description(createFood.getDescription())
                .quantity(createFood.getQuantity())
                .star(0.0)
                .category(category)
                .user(user)
                .reviews(new ArrayList<>())
                .createdBy(user.getUsername())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedBy(user.getUsername())
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        food = foodRepository.save(food);
        List<FoodImage> foodImages = new ArrayList<>();
        if (createFood.getImage() != null) {
            for (MultipartFile imageFile : createFood.getImage()) {
                String generatedFileName = storageService.storeFile(imageFile);
                FoodImage foodImage = FoodImage.builder()
                        .imageUrl("http://localhost:8080/api/v1/FileUpload/files/" + generatedFileName)
                        .food(food)
                        .createdBy(user.getUsername())
                        .modifiedBy(user.getUsername())
                        .build();
                foodImages.add(foodImage);
            }
        }
        foodImageRepository.saveAll(foodImages);
        return foodMapper.toFoodDTO(food);
    }


    public FoodDTO update(EditFood editFood, User user) {
        Food food = foodRepository.findById(editFood.getId())
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        if (editFood.getImage() != null && !editFood.getImage().isEmpty()) {
            food.getImages().clear();
            for (MultipartFile imageFile : editFood.getImage()) {
                try {
                    String generatedFileName = storageService.storeFile(imageFile);
                    FoodImage foodImage = FoodImage.builder()
                            .imageUrl("http://localhost:8080/api/v1/FileUpload/files/" + generatedFileName)
                            .food(food)
                            .build();
                    food.getImages().add(foodImage);
                } catch (Exception e) {
                    throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                }
            }
        }
        food.setName(editFood.getName());
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
            if (foodRepository.existsById(id)) {
                foodRepository.deleteById(id);
            } else {
                throw new AppException(ErrorCode.FOOD_NOTFOUND);
            }
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
        return foodRepository.findAll().stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }

    @Override
    public List<FoodDTO> findAllByCategoryId(Long categoryId) {
        return foodRepository.findAllByCategoryId(categoryId).stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }

    @Override
    public List<FoodDTO> findAllByPriceGreaterThan(Double price) {
        return foodRepository.findAllByPriceGreaterThan(price).stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }

    @Override
    public List<FoodDTO> findAllByPriceLessThan(Double price) {
        return foodRepository.findAllByPriceLessThan(price).stream()
                .map(foodMapper::toFoodDTO)
                .toList();
    }
}
