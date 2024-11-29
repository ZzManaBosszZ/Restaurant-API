package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.food.FoodDTO;
import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.food.CreateFood;
import com.restaurant.restaurantapi.models.food.EditFood;
import com.restaurant.restaurantapi.models.menu.CreateMenu;
import com.restaurant.restaurantapi.models.menu.EditMenu;

import com.restaurant.restaurantapi.services.impl.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<ResponseObject> create( @RequestParam("name") String name,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("image") MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CreateMenu createMenu = new CreateMenu();
        createMenu.setName(name);
        createMenu.setDescription(description);
        createMenu.setImage(image);
        menuService.create(createMenu, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(true, 200, "Create Success", "")
        );
    }

    @PutMapping
    public ResponseEntity<ResponseObject> update(  @RequestParam("id") Long id,
                                                   @RequestParam("name") String name,
                                                   @RequestParam("description") String description,
                                                   @RequestParam(value = "image", required = false) MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        EditMenu editMenu = new EditMenu();
        editMenu.setId(id);
        editMenu.setName(name);
        editMenu.setDescription(description);
        editMenu.setImage(image);
        MenuDTO menuDTO = menuService.update(editMenu, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Menu updated successfully", menuDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long[] id) {
        menuService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Menu deleted successfully", null)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        MenuDTO menuDTO = menuService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Menu found", menuDTO)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        List<MenuDTO> menus = menuService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Menus retrieved successfully", menus)
        );
    }
}