package com.restaurant.restaurantapi.controllers;
import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.ordertable.OrderTableDTO;
import com.restaurant.restaurantapi.models.ordertable.CreateOrderTable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.restaurant.restaurantapi.services.impl.OrderTableService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/any/ordertables")
@RequiredArgsConstructor
public class OrderTableController {

    private final OrderTableService orderTableService;

    @PostMapping
    public ResponseEntity<ResponseObject> createOrderTable(@RequestBody CreateOrderTable createOrderTable) {
        OrderTableDTO orderTableDTO = orderTableService.createOrderTable(createOrderTable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", orderTableDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> acceptOrderTable(@PathVariable Long id) {
        OrderTableDTO acceptedOrderTableDTO = orderTableService.acceptOrderTable(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Order table accepted successfully", acceptedOrderTableDTO)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderTableById(@PathVariable Long id) {
        OrderTableDTO orderTableDTO = orderTableService.getOrderTableById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", orderTableDTO)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllOrderTables() {
        List<OrderTableDTO> orderTableDTOs = orderTableService.getAllOrderTables();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", orderTableDTOs)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderTable(@PathVariable Long id) {
        orderTableService.deleteOrderTable(id);
        return ResponseEntity.noContent().build();
    }
}
