package com.restaurant.restaurantapi.services;
import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.dtos.menufood.MenuFoodDTO;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.Menu;
import com.restaurant.restaurantapi.entities.MenuFood;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.MenuFoodMapper;
import com.restaurant.restaurantapi.models.menufood.CreateMenuFood;
import com.restaurant.restaurantapi.models.menufood.EditMenuFood;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.repositories.MenuFoodRepository;
import com.restaurant.restaurantapi.repositories.MenuRepository;
import com.restaurant.restaurantapi.services.impl.MenuFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IMenuFoodService implements MenuFoodService {

    private final MenuFoodRepository menuFoodRepository;
    private final MenuFoodMapper menuFoodMapper;
    private final FoodRepository foodRepository;
    private final MenuRepository menuRepository;


    @Override
    public MenuFoodDTO create(CreateMenuFood createMenuFood, User user) {
        Food food = foodRepository.findById(createMenuFood.getFoodId())
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        Menu menu = menuRepository.findById(createMenuFood.getMenuId())
                .orElseThrow(() -> new AppException(ErrorCode.MENU_NOTFOUND));
        MenuFood menuFood = MenuFood.builder()
                .food(food)
                .menu(menu)
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();
        menuFoodRepository.save(menuFood);
        return menuFoodMapper.toMenuFoodDTO(menuFood);
    }

    @Override
    public MenuFoodDTO update(EditMenuFood editMenuFood, User user) {
        MenuFood menuFoodExisting = menuFoodRepository.findById(editMenuFood.getId())
                .orElseThrow(() -> new AppException(ErrorCode.MENU_FOOD_NOTFOUND));
        Menu menu = menuRepository.findById(editMenuFood.getMenuId())
                .orElseThrow(() -> new AppException(ErrorCode.MENU_NOTFOUND));
        Food food = foodRepository.findById(editMenuFood.getFoodId())
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        menuFoodExisting.setMenu(menu);
        menuFoodExisting.setFood(food);
        menuFoodExisting.setModifiedBy(user.getFullName());
        menuFoodExisting.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        menuFoodRepository.save(menuFoodExisting);
        return menuFoodMapper.toMenuFoodDTO(menuFoodExisting);
    }


    @Override
    public void delete(Long[] ids) {
        menuFoodRepository.deleteAllById(List.of(ids));
    }

    @Override
    public MenuFoodDTO findById(Long id) {
        MenuFood menuFood = menuFoodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MENU_FOOD_NOTFOUND));
        return menuFoodMapper.toMenuFoodDTO(menuFood);
    }

    @Override
    public List<MenuFoodDTO> findAll() {
        return menuFoodRepository.findAll().stream()
                .map(menuFoodMapper::toMenuFoodDTO)
                .collect(Collectors.toList());
    }
}
