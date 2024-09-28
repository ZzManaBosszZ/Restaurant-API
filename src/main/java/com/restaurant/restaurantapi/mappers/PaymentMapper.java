package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.payment.PaymentDTO;
import com.restaurant.restaurantapi.entities.Payment;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDTO toPaymentDTO(Payment model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .id(model.getId())
                .paymentDate(model.getPaymentDate())
                .paymentMethod(model.getPaymentMethod())
                .isPaid(model.isPaid())
                .price(model.getPrice())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();

        if (model.getUser() != null) {
            paymentDTO.setUserId(model.getUser().getId());
        }

        if (model.getOrder() != null) {
            paymentDTO.setOrderId(model.getOrder().getId());
        }
        return paymentDTO;
    }
}
