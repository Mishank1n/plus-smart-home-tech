package ru.yandex.practicum.service;

import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.payment.dto.PaymentDto;

import java.math.BigDecimal;

public interface PaymentService {

    BigDecimal calculateProductsCost(OrderDto order);

    BigDecimal calculateTotalCost(OrderDto order);

    void successPayment(String orderId);

    void failPayment(String orderId);

    PaymentDto put(OrderDto order);
}
