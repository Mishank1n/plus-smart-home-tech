package ru.yandex.practicum.mapper;

import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.payment.dto.PaymentDto;

public class PaymentMapper {

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .deliveryTotal(payment.getDeliveryTotal())
                .orderId(payment.getOrderId())
                .paymentState(payment.getPaymentState())
                .totalPayment(payment.getTotalPayment())
                .feeTotal(payment.getFeeTotal())
                .productTotal(payment.getProductTotal())
                .build();
    }
}