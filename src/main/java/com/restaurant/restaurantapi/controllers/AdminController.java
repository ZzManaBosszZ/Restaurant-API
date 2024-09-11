package com.restaurant.restaurantapi.controllers;
import com.restaurant.restaurantapi.dtos.menuadmin.Menu;
import com.restaurant.restaurantapi.dtos.orders.*;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.services.impl.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurant.restaurantapi.dtos.ResponseObject;

import java.util.List;


@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;



    @GetMapping("menu")
    public ResponseEntity<ResponseObject> getMenu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<Menu> menuItems = adminService.getMenu(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/total-orders")
    public ResponseEntity<ResponseObject> getTotalOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        TotalOrderDTO totalOrderDTO = adminService.getTotalOrders(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", totalOrderDTO)
        );
    }

    @GetMapping("/delivered-orders")
    public ResponseEntity<ResponseObject> getDeliveredOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        DeliveredOrderDTO deliveredOrderDTO = adminService.getDeliveredOrders(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", deliveredOrderDTO)
        );
    }

    @GetMapping("/cancelled-orders")
    public ResponseEntity<ResponseObject> getCancelledOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CancelledOrderDTO cancelledOrderDTO = adminService.getCancelledOrders(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", cancelledOrderDTO)
        );
    }


    @GetMapping("/total-revenue")
    public ResponseEntity<ResponseObject> getTotalRevenue() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        TotalRevenueDTO totalRevenueDTO = adminService.getTotalRevenue(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", totalRevenueDTO)
        );
    }

    @GetMapping("/daily-revenue")
    public ResponseEntity<ResponseObject> getDailyRevenue() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        DailyRevenueDTO dailyRevenue = adminService.getDailyRevenue(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok" , dailyRevenue)
        );
    }

    @GetMapping("/daily-revenue")
    public ResponseEntity<ResponseObject> getDailyRevenue() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        DailyRevenueDTO dailyRevenue = adminService.getDailyRevenue(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok" , dailyRevenue)
        );
    }

}
