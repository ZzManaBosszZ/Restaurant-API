package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.ordertable.OrderTableDTO;
import com.restaurant.restaurantapi.entities.Menu;
import com.restaurant.restaurantapi.entities.OrderTable;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.OrderTableMapper;
import com.restaurant.restaurantapi.models.ordertable.CreateOrderTable;
import com.restaurant.restaurantapi.repositories.MenuRepository;
import com.restaurant.restaurantapi.repositories.OrderTableRepository;

import com.restaurant.restaurantapi.services.impl.OrderTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IOrderTableService implements OrderTableService {

    private final OrderTableRepository orderTableRepository;
    private final MenuRepository menuRepository;
    private final OrderTableMapper orderTableMapper;

    @Override
    public OrderTableDTO createOrderTable(CreateOrderTable createOrderTable) {
        Menu menu = menuRepository.findById(createOrderTable.getMenuId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        OrderTable orderTable = OrderTable.builder()
                .name(createOrderTable.getName())
                .numberOfPerson(createOrderTable.getNumberOfPerson())
                .email(createOrderTable.getEmail())
                .phone(createOrderTable.getPhone())
                .time(createOrderTable.getTime())
                .date(createOrderTable.getDate())
                .menu(menu)
                .build();
        return orderTableMapper.toOrderTableDTO(orderTableRepository.save(orderTable));
    }

    @Override
    public OrderTableDTO getOrderTableById(Long id) {
        OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        return orderTableMapper.toOrderTableDTO(orderTable);
    }

    @Override
    public List<OrderTableDTO> getAllOrderTables() {
        return orderTableRepository.findAll().stream().map(orderTableMapper::toOrderTableDTO).collect(Collectors.toList());
    }

//    @Override
//    public OrderTableDTO updateOrderTable(Long id, CreateOrderTable updateOrderTable) {
//        OrderTable orderTable = orderTableRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
//
//        Menu menu = menuRepository.findById(updateOrderTable.getMenuId())
//                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
//
//        orderTable.setName(updateOrderTable.getName());
//        orderTable.setNumberOfPerson(updateOrderTable.getNumberOfPerson());
//        orderTable.setEmail(updateOrderTable.getEmail());
//        orderTable.setPhone(updateOrderTable.getPhone());
//        orderTable.setTime(updateOrderTable.getTime());
//        orderTable.setDate(updateOrderTable.getDate());
//        orderTable.setMenu(menu);
//
//        return orderTableMapper.toOrderTableDTO(orderTableRepository.save(orderTable));
//    }

    @Override
    public void deleteOrderTable(Long id) {
        OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));

        orderTableRepository.delete(orderTable);
    }
}
