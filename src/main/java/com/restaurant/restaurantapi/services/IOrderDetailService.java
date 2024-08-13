package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.Food;
import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.OrderDetailMapper;
import com.restaurant.restaurantapi.models.orderdetail.CreateOrderDetail;
import com.restaurant.restaurantapi.repositories.OrderDetailRepository;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.services.impl.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IOrderDetailService implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final FoodRepository foodRepository;
    private final OrdersRepository ordersRepository;


    @Transactional
    @Override
    public OrderDetailDTO createOrderDetail(Long orderId, CreateOrderDetail createOrderDetail) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Food food = foodRepository.findById(createOrderDetail.getFoodId())
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .food(food)
                .quantity(createOrderDetail.getQuantity())
                .unitPrice(createOrderDetail.getUnitPrice())
                .discount(createOrderDetail.getDiscount())
                .build();
        orderDetailRepository.save(orderDetail);

        return orderDetailMapper.toOrderDetailDTO(orderDetail);
    }
    @Override
    public List<OrderDetailDTO> findByOrderId(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        return orderDetails.stream()
                .map(orderDetailMapper::toOrderDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO findById(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

        return orderDetailMapper.toOrderDetailDTO(orderDetail);
    }


}
