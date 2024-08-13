package com.restaurant.restaurantapi.controllers;

import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.models.menu.CreateMenu;
import com.restaurant.restaurantapi.models.menu.EditMenu;

import com.restaurant.restaurantapi.services.impl.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody CreateMenu createMenu) {
        MenuDTO menuDTO = menuService.create(createMenu);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(true, 201, "Menu created successfully", menuDTO)
        );
    }

    @PutMapping
    public ResponseEntity<ResponseObject> update(@RequestBody EditMenu editMenu) {
        MenuDTO menuDTO = menuService.update(editMenu);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Menu updated successfully", menuDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
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
