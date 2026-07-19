package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.payment.PaymentDTO;
import com.restaurant.restaurantapi.dtos.paypal.PayPalCaptureResult;
import com.restaurant.restaurantapi.dtos.paypal.PayPalCreateOrderResponse;
import com.restaurant.restaurantapi.dtos.paypal.capture.PayPalCaptureOrderApiResponse;
import com.restaurant.restaurantapi.dtos.paypal.capture.PayPalCapturePurchaseUnitResponse;
import com.restaurant.restaurantapi.dtos.paypal.capture.PayPalCaptureResponse;
import com.restaurant.restaurantapi.dtos.paypal.order.response.PayPalCreateOrderApiResponse;
import com.restaurant.restaurantapi.entities.*;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.PaymentMapper;
import com.restaurant.restaurantapi.models.payment.CreatePayment;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.repositories.PaymentRepository;
import com.restaurant.restaurantapi.services.impl.PaymentService;
import com.restaurant.restaurantapi.services.paypal.PayPalClient;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private final PayPalClient payPalClient;

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
                .currency("USD")
                .isPaid(false)
                .status(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toPaymentDTO(savedPayment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayPalCreateOrderResponse createPayPalOrder(
            Long orderId,
            User currentUser
    ) {
        Orders order = ordersRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.NOTFOUND)
                );

        // Không cho user khác tạo payment cho order này.
        if (order.getUser() == null
                || !order.getUser()
                .getId()
                .equals(currentUser.getId())) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        // Order đã thanh toán thì dừng.
        if (order.isPaid()
                || order.getStatus() == OrderStatus.paid
                || order.getStatus() == OrderStatus.completed) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }

        // Order bị hủy không được thanh toán.
        if (order.getStatus() == OrderStatus.cancelled) {
            throw new IllegalStateException(
                    "Cancelled order cannot be paid"
            );
        }

        Payment payment = paymentRepository
                .findByOrderAndUser(order, currentUser)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Payment has not been initialized"
                        )
                );

        if (payment.isPaid()
                || payment.getStatus()
                == PaymentStatus.COMPLETED) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }

        // Nếu đã có PayPal Order ID thì trả lại,
        // không tạo PayPal Order mới.
        if (payment.getPaypalOrderId() != null
                && !payment.getPaypalOrderId().isBlank()) {
            return PayPalCreateOrderResponse.builder()
                    .orderId(order.getId())
                    .paymentId(payment.getId())
                    .paypalOrderId(
                            payment.getPaypalOrderId()
                    )
                    .paypalStatus("CREATED")
                    .amount(payment.getPrice())
                    .currency(payment.getCurrency())
                    .build();
        }

        // Một request ID cố định cho payment này.
        // Nếu PayPal timeout và backend retry,
        // backend vẫn gửi cùng request ID.
        if (payment.getPaypalRequestId() == null
                || payment.getPaypalRequestId().isBlank()) {
            payment.setPaypalRequestId(
                    "create-payment-" + payment.getId()
            );

            payment = paymentRepository.save(payment);
        }

        PayPalCreateOrderApiResponse paypalResponse =
                payPalClient.createOrder(payment);

        payment.setPaypalOrderId(
                paypalResponse.getId()
        );

        Payment savedPayment =
                paymentRepository.save(payment);

        return PayPalCreateOrderResponse.builder()
                .orderId(order.getId())
                .paymentId(savedPayment.getId())
                .paypalOrderId(
                        savedPayment.getPaypalOrderId()
                )
                .paypalStatus(
                        paypalResponse.getStatus()
                )
                .amount(savedPayment.getPrice())
                .currency(savedPayment.getCurrency())
                .build();
    }

    private PayPalCaptureResult buildCaptureResult(
            Orders order,
            Payment payment
    ) {
        return PayPalCaptureResult.builder()
                .orderId(order.getId())
                .paymentId(payment.getId())
                .paypalOrderId(
                        payment.getPaypalOrderId()
                )
                .paypalCaptureId(
                        payment.getPaypalCaptureId()
                )
                .paymentStatus(
                        payment.getStatus().name()
                )
                .orderStatus(
                        order.getStatus().name()
                )
                .amount(payment.getPrice())
                .currency(payment.getCurrency())
                .build();
    }

    private String limitMessage(String message) {
        if (message == null) {
            return "Unknown PayPal capture error";
        }

        return message.length() <= 500
                ? message
                : message.substring(0, 500);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayPalCaptureResult capturePayPalOrder(
            String paypalOrderId,
            User currentUser
    ) {
        Payment payment = paymentRepository
                .findByPaypalOrderId(paypalOrderId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Payment not found"
                        )
                );

        Orders order = payment.getOrder();

        if (order == null) {
            throw new IllegalStateException(
                    "Payment does not have an order"
            );
        }

        // Không cho user khác capture order.
        if (payment.getUser() == null
                || !payment.getUser()
                .getId()
                .equals(currentUser.getId())) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        if (order.getUser() == null
                || !order.getUser()
                .getId()
                .equals(currentUser.getId())) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        // Nếu đã capture thành công trước đó thì trả lại.
        if (payment.isPaid()
                && payment.getStatus()
                == PaymentStatus.COMPLETED
                && payment.getPaypalCaptureId() != null) {

            return buildCaptureResult(
                    order,
                    payment
            );
        }

        if (order.isPaid()
                || order.getStatus() == OrderStatus.paid
                || order.getStatus() == OrderStatus.completed) {

            if (payment.getStatus()
                    == PaymentStatus.COMPLETED) {
                return buildCaptureResult(
                        order,
                        payment
                );
            }

            throw new AppException(
                    ErrorCode.ALREADY_PAID
            );
        }

        if (order.getStatus()
                == OrderStatus.cancelled) {
            throw new IllegalStateException(
                    "Cancelled order cannot be captured"
            );
        }

        if (payment.getPaypalOrderId() == null
                || payment.getPaypalOrderId().isBlank()) {
            throw new IllegalStateException(
                    "PayPal order has not been created"
            );
        }

        if (!payment.getPaypalOrderId()
                .equals(paypalOrderId)) {
            throw new IllegalStateException(
                    "PayPal order ID does not match payment"
            );
        }

        if (payment.getCaptureRequestId() == null
                || payment.getCaptureRequestId().isBlank()) {

            payment.setCaptureRequestId(
                    "capture-payment-" + payment.getId()
            );

            payment = paymentRepository.save(payment);
        }

        try {
            PayPalCaptureOrderApiResponse response =
                    payPalClient.captureOrder(
                            paypalOrderId,
                            payment.getCaptureRequestId()
                    );

            PayPalCaptureResponse capture =
                    extractCompletedCapture(response);

            validateCapture(
                    payment,
                    response,
                    capture
            );

            payment.setPaypalCaptureId(
                    capture.getId()
            );

            payment.setStatus(
                    PaymentStatus.COMPLETED
            );

            payment.setPaid(true);

            payment.setPaymentDate(
                    LocalDateTime.now()
            );

            payment.setFailureReason(null);

            order.setPaid(true);

            order.setStatus(
                    OrderStatus.paid
            );

            Payment savedPayment =
                    paymentRepository.save(payment);

            Orders savedOrder =
                    ordersRepository.save(order);

            return buildCaptureResult(
                    savedOrder,
                    savedPayment
            );

        } catch (RuntimeException exception) {
            payment.setFailureReason(
                    limitMessage(exception.getMessage())
            );

            /*
             * Không nên luôn đặt FAILED vì có thể lỗi mạng,
             * trong khi PayPal đã capture thành công.
             *
             * Giữ PENDING để có thể kiểm tra/retry.
             */
            paymentRepository.save(payment);

            throw exception;
        }
    }

    private PayPalCaptureResponse extractCompletedCapture(
            PayPalCaptureOrderApiResponse response
    ) {
        if (response == null
                || response.getPurchaseUnits() == null
                || response.getPurchaseUnits().isEmpty()) {
            throw new IllegalStateException(
                    "PayPal capture response has no purchase units"
            );
        }

        return response.getPurchaseUnits()
                .stream()
                .filter(unit ->
                        unit.getPayments() != null
                )
                .filter(unit ->
                        unit.getPayments().getCaptures() != null
                )
                .flatMap(unit ->
                        unit.getPayments()
                                .getCaptures()
                                .stream()
                )
                .filter(capture ->
                        "COMPLETED".equalsIgnoreCase(
                                capture.getStatus()
                        )
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "PayPal capture is not completed"
                        )
                );
    }

    private void validateCapture(
            Payment payment,
            PayPalCaptureOrderApiResponse response,
            PayPalCaptureResponse capture
    ) {
        if (!payment.getPaypalOrderId()
                .equals(response.getId())) {
            throw new IllegalStateException(
                    "Captured PayPal order ID does not match"
            );
        }

        if (capture.getId() == null
                || capture.getId().isBlank()) {
            throw new IllegalStateException(
                    "PayPal capture ID is missing"
            );
        }

        if (capture.getAmount() == null) {
            throw new IllegalStateException(
                    "PayPal capture amount is missing"
            );
        }

        String capturedCurrency =
                capture.getAmount().getCurrencyCode();

        if (capturedCurrency == null
                || !capturedCurrency.equalsIgnoreCase(
                payment.getCurrency()
        )) {
            throw new IllegalStateException(
                    "PayPal currency does not match payment"
            );
        }

        BigDecimal capturedAmount;

        try {
            capturedAmount = new BigDecimal(
                    capture.getAmount().getValue()
            );
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "PayPal capture amount is invalid",
                    exception
            );
        }

        BigDecimal expectedAmount =
                payment.getPrice()
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        capturedAmount = capturedAmount.setScale(
                2,
                RoundingMode.HALF_UP
        );

        if (capturedAmount.compareTo(
                expectedAmount
        ) != 0) {
            throw new IllegalStateException(
                    "Captured amount does not match payment amount"
            );
        }

        PayPalCapturePurchaseUnitResponse purchaseUnit =
                response.getPurchaseUnits()
                        .stream()
                        .findFirst()
                        .orElseThrow();

        String expectedOrderId =
                String.valueOf(
                        payment.getOrder().getId()
                );

        if (purchaseUnit.getCustomId() != null
                && !purchaseUnit.getCustomId()
                .equals(expectedOrderId)) {
            throw new IllegalStateException(
                    "PayPal custom ID does not match order"
            );
        }
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
