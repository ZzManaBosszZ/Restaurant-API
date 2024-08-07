package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.food.CreateFood;
import com.restaurant.restaurantapi.models.food.EditFood;

import com.restaurant.restaurantapi.services.impl.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/any/food")
    public ResponseEntity<ResponseObject> getAll() {
        List<FoodDTO> list = foodService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/food/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") Long id) {
        FoodDTO foodDTO = foodService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", foodDTO)
        );
    }

    @GetMapping("/food/category/{categoryId}")
    public ResponseEntity<ResponseObject> getByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<FoodDTO> foodDTOList = foodService.findAllByCategoryId(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", foodDTOList)
        );
    }

    @GetMapping("/food/price-greater-than/{price}")
    public ResponseEntity<ResponseObject> getByPriceGreaterThan(@PathVariable("price") Double price) {
        List<FoodDTO> foodDTOList = foodService.findAllByPriceGreaterThan(price);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", foodDTOList)
        );
    }

    @GetMapping("/food/price-less-than/{price}")
    public ResponseEntity<ResponseObject> getByPriceLessThan(@PathVariable("price") Double price) {
        List<FoodDTO> foodDTOList = foodService.findAllByPriceLessThan(price);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", foodDTOList)
        );
    }

    @PostMapping("/food")
    public ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateFood createFood) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        FoodDTO foodDTO = foodService.create(createFood, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", foodDTO)
        );
    }

    @PutMapping("/food")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody EditFood editFood) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        FoodDTO foodDTO = foodService.update(editFood, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Update Success", foodDTO)
        );
    }

    @DeleteMapping("/food")
    public ResponseEntity<ResponseObject> delete(@RequestBody Long[] ids) {
        foodService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Delete Success", "")
        );
    }
}
