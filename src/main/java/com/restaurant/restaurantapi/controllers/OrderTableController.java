package com.restaurant.restaurantapi.controllers;
import com.restaurant.restaurantapi.dtos.ordertable.OrderTableDTO;
import com.restaurant.restaurantapi.models.ordertable.CreateOrderTable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.restaurant.restaurantapi.services.impl.OrderTableService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ordertables")
@RequiredArgsConstructor
public class OrderTableController {

    private final OrderTableService orderTableService;

    @PostMapping
    public ResponseEntity<OrderTableDTO> createOrderTable(@RequestBody CreateOrderTable createOrderTable) {
        OrderTableDTO orderTableDTO = orderTableService.createOrderTable(createOrderTable);
        return ResponseEntity.ok(orderTableDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTableDTO> getOrderTableById(@PathVariable Long id) {
        OrderTableDTO orderTableDTO = orderTableService.getOrderTableById(id);
        return ResponseEntity.ok(orderTableDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderTableDTO>> getAllOrderTables() {
        List<OrderTableDTO> orderTableDTOs = orderTableService.getAllOrderTables();
        return ResponseEntity.ok(orderTableDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderTable(@PathVariable Long id) {
        orderTableService.deleteOrderTable(id);
        return ResponseEntity.noContent().build();
    }
}
