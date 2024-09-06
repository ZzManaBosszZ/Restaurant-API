package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.payment.PaymentDTO;
import com.restaurant.restaurantapi.entities.*;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.PaymentMapper;
import com.restaurant.restaurantapi.models.payment.CreatePayment;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.repositories.PaymentRepository;
import com.restaurant.restaurantapi.services.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IPaymentService implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    @Override
    public PaymentDTO payment(CreatePayment createPayment, User currentUser) {
        Orders order = ordersRepository.findById(createPayment.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        if (order.getStatus().equals(OrderStatus.pending) || order.getStatus().equals(OrderStatus.cancelled)) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }
        Payment paymentExisting = paymentRepository.findByOrderAndUser(order, currentUser);
        if (paymentExisting != null) throw new AppException(ErrorCode.ALREADY_PAID);
        Payment payment = Payment.builder()
                .paymentMethod(createPayment.getPaymentMethod())
                .paymentDate(LocalDateTime.now())
                .order(order)
                .price(createPayment.getPrice())
                .isPaid(true)
                .build();
        paymentRepository.save(payment);
        Orders order1 = Orders.builder()
                .isPaid(true)
                .status(OrderStatus.pending)
                .build();
        ordersRepository.save(order1);
        return paymentMapper.toPaymentDTO(payment);
    }
}
