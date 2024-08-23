package com.restaurant.restaurantapi.services;
import com.restaurant.restaurantapi.entities.*;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.mappers.OrdersMapper;
import com.restaurant.restaurantapi.models.orders.CreateOrders;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.repositories.UserRepository;
import com.restaurant.restaurantapi.services.impl.OrdersService;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
    public class IOrdersService implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;



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
                .isPaid(false)
                .status(OrderStatus.pending)
                .user(user)
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        List<OrderDetail> orderDetails = createOrders.getOrderDetails().stream()
                .map(detail -> {
                    Food food = foodRepository.findById(detail.getFoodId())
                            .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
                    return OrderDetail.builder()
                            .order(order)
                            .food(food)
                            .quantity(detail.getQuantity())
                            .unitPrice(food.getPrice())
                            .discount(detail.getDiscount())
                            .createdDate(new Timestamp(System.currentTimeMillis()))
                            .modifiedDate(new Timestamp(System.currentTimeMillis()))
                            .createdBy(user.getFullName())
                            .modifiedBy(user.getFullName())
                            .build();
                })
                .collect(Collectors.toList());
        order.setOrderDetails(orderDetails);
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
