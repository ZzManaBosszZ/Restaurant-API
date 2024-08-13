package com.restaurant.restaurantapi.services;
import org.springframework.security.core.Authentication;
import com.restaurant.restaurantapi.entities.OrderIsPaid;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.mappers.OrdersMapper;
import com.restaurant.restaurantapi.models.orders.CreateOrders;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.repositories.UserRepository;
import com.restaurant.restaurantapi.services.impl.OrdersService;
import com.restaurant.restaurantapi.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
    public class IOrdersService implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserRepository userRepository;

    private String generateOrderCode() {
        return UUID.randomUUID().toString();
    }
    @Transactional(rollbackFor = AppException.class)
    @Override
    public OrdersDTO create(CreateOrders createOrders , User user) throws AppException {
        String orderCode = generateOrderCode();

        Orders order = Orders.builder()
                .orderCode(orderCode)
                .total(createOrders.getTotal())
                .isPaid(OrderIsPaid.pending)
                .status(createOrders.getStatus())
                .user(user)
//                .orderDetails(new ArrayList<>())
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        Orders savedOrder = ordersRepository.save(order);
        return ordersMapper.toOrdersDTO(savedOrder);
    }

    @Override
    public OrdersDTO findById(Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return ordersMapper.toOrdersDTO(order);
    }

    @Override
    public List<OrdersDTO> findAll() {
        return ordersRepository.findAll().stream()
                .map(ordersMapper::toOrdersDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        ordersRepository.delete(order);
    }
}
