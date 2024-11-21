package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.menufood.MenuFoodDTO;
import com.restaurant.restaurantapi.models.menufood.CreateMenuFood;
import com.restaurant.restaurantapi.models.menufood.EditMenuFood;
import com.restaurant.restaurantapi.services.impl.MenuFoodService;
import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MenuFoodController {

    private final MenuFoodService menuFoodService;

    @PostMapping("/menu-food")
    public ResponseEntity<ResponseObject> createMenuFood(@RequestBody CreateMenuFood createMenuFood) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject(false, 401, "User not authenticated", null)
            );
        }
        try {
            MenuFoodDTO menuFoodDTO = menuFoodService.create(createMenuFood, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject(true, 201, "Create Success", menuFoodDTO)
            );
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null)
            );
        }
    }

    @PutMapping("/menu-food/{id}")
    public ResponseEntity<ResponseObject> updateMenuFood(@PathVariable Long id, @RequestBody EditMenuFood editMenuFood) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject(false, 401, "User not authenticated", null)
            );
        }
        try {
            editMenuFood.setId(id);
            MenuFoodDTO menuFoodDTO = menuFoodService.update(editMenuFood, currentUser);
            return ResponseEntity.ok(
                    new ResponseObject(true, HttpStatus.OK.value(), "Update Success", menuFoodDTO)
            );
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/menu-food/{id}")
    public ResponseEntity<ResponseObject> deleteMenuFood(@PathVariable Long id) {
        try {
            menuFoodService.delete(new Long[]{id});
            return ResponseEntity.noContent().build();
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null)
            );
        }
    }

    @GetMapping("/menu-food/{id}")
    public ResponseEntity<ResponseObject> findMenuFoodById(@PathVariable Long id) {
        try {
            MenuFoodDTO menuFoodDTO = menuFoodService.findById(id);
            return ResponseEntity.ok(
                    new ResponseObject(true, HttpStatus.OK.value(), "Find Success", menuFoodDTO)
            );
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, HttpStatus.NOT_FOUND.value(), e.getMessage(), null)
            );
        }
    }

    @GetMapping("/any/menu-food")
    public ResponseEntity<ResponseObject> findAllMenuFoods() {
        List<MenuFoodDTO> menuFoodDTOs = menuFoodService.findAll();
        return ResponseEntity.ok(
                new ResponseObject(true, HttpStatus.OK.value(), "Find All Success", menuFoodDTOs)
        );
    }
}