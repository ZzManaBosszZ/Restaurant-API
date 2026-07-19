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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IPaymentService implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentDTO payment(
            CreatePayment createPayment,
            User currentUser
    ) {
        Orders order = ordersRepository
                .findById(createPayment.getOrderId())
                .orElseThrow(() ->
                        new AppException(ErrorCode.NOTFOUND)
                );

        // Chỉ chủ sở hữu order mới được khởi tạo thanh toán.
        if (order.getUser() == null
                || !order.getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        // Order đã thanh toán thì không tạo payment mới.
        if (order.isPaid()
                || order.getStatus() == OrderStatus.paid
                || order.getStatus() == OrderStatus.completed) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }

        // Order bị hủy thì không được thanh toán.
        if (order.getStatus() == OrderStatus.cancelled) {
            throw new IllegalStateException(
                    "Cancelled order cannot be paid"
            );
        }

        Payment paymentExisting = paymentRepository
                .findByOrderAndUser(order, currentUser)
                .orElse(null);

        // Nếu đã có payment pending thì trả lại payment cũ,
        // không tạo thêm bản ghi trùng.
        if (paymentExisting != null) {
            if (paymentExisting.isPaid()
                    || paymentExisting.getStatus()
                    == PaymentStatus.COMPLETED) {
                throw new AppException(ErrorCode.ALREADY_PAID);
            }

            return paymentMapper.toPaymentDTO(paymentExisting);
        }

        Payment payment = Payment.builder()
                .paymentMethod(createPayment.getPaymentMethod())
                .paymentDate(null)
                .order(order)
                .user(currentUser)
                .price(order.getTotal())
                .isPaid(false)
                .status(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // Không thay đổi isPaid và status của order ở đây.
        // Order vẫn pending cho đến khi backend xác thực PayPal.

        return paymentMapper.toPaymentDTO(savedPayment);
    }

//    @Override
//    public PaymentDTO payment(CreatePayment createPayment, User currentUser) {
//        Orders order = ordersRepository.findById(createPayment.getId())
//                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
//        if (order.getStatus().equals(OrderStatus.pending) || order.getStatus().equals(OrderStatus.cancelled)) {
//            throw new AppException(ErrorCode.ALREADY_PAID);
//        }
//        Payment paymentExisting = paymentRepository.findByOrderAndUser(order, currentUser);
//        if (paymentExisting != null) throw new AppException(ErrorCode.ALREADY_PAID);
//        Payment payment = Payment.builder()
//                .paymentMethod(createPayment.getPaymentMethod())
//                .paymentDate(LocalDateTime.now())
//                .order(order)
////                .price(createPayment.getPrice())
//                .isPaid(true)
//                .build();
//        paymentRepository.save(payment);
//        Orders order1 = Orders.builder()
//                .isPaid(true)
//                .status(OrderStatus.pending)
//                .build();
//        ordersRepository.save(order1);
//        return paymentMapper.toPaymentDTO(payment);
//    }
}
