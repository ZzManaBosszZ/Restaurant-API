package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.category.CategoryDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.category.CreateCategory;
import com.restaurant.restaurantapi.models.category.EditCategory;
import com.restaurant.restaurantapi.services.impl.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/any/category")
    ResponseEntity<ResponseObject> getAll() {
        List<CategoryDTO> list = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
    @GetMapping("/category/{slug}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug) {
        CategoryDTO categoryDTO = categoryService.findBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", categoryDTO)
        );
    }
    @PostMapping("/category")
    ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateCategory createCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CategoryDTO categoryDTO = categoryService.create(createCategory, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", categoryDTO)
        );
    }
    @PutMapping("/category")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody EditCategory editCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CategoryDTO categoryDTO = categoryService.update(editCategory, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Update Success", categoryDTO)
        );
    }

    @DeleteMapping("/category")
    ResponseEntity<ResponseObject> update(@RequestBody Long[] ids) {
        categoryService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Delete success", "")
        );
    }

}