package ru.yandex.practicum.mapper;

import ru.yandex.practicum.delivery.dto.DeliveryDto;
import ru.yandex.practicum.model.Delivery;

public class DeliveryMapper {

    public static DeliveryDto toDto(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .deliveryState(delivery.getDeliveryState())
                .fromAddress(AddressMapper.toDto(delivery.getFromAddress()))
                .toAddress(AddressMapper.toDto(delivery.getToAddress()))
                .orderId(delivery.getOrderId())
                .build();
    }

    public static Delivery toModel(DeliveryDto deliveryDto) {
        return Delivery.builder()
                .orderId(deliveryDto.getOrderId())
                .deliveryState(deliveryDto.getDeliveryState())
                .build();
    }
}
