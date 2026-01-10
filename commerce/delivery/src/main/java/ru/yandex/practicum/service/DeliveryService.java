package ru.yandex.practicum.service;

import ru.yandex.practicum.delivery.dto.DeliveryDto;
import ru.yandex.practicum.order.dto.OrderDto;

import java.math.BigDecimal;

public interface DeliveryService {

    DeliveryDto put(DeliveryDto deliveryDto);

    void successDelivery(String deliveryId);

    void receivingProduct(String deliveryId);

    void failDelivery(String deliveryId);

    BigDecimal calculateDeliveryCost(OrderDto order);
}
