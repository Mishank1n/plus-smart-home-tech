package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.delivery.dto.DeliveryDto;
import ru.yandex.practicum.delivery.dto.DeliveryState;
import ru.yandex.practicum.delivery.exception.DeliveryIsAlreadyContainException;
import ru.yandex.practicum.delivery.exception.NoDeliveryFoundException;
import ru.yandex.practicum.mapper.AddressMapper;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.order.client.OrderClient;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.payment.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.repository.AddressRepository;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.warehouse.client.WarehouseClient;
import ru.yandex.practicum.warehouse.dto.ShippedToDeliveryRequest;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class DeliveryServiceImp implements DeliveryService {

    private final String deliveryNotFoundErrorMessage = "Доставка с id = %s не найдена";

    @Autowired
    private final DeliveryRepository repository;

    @Autowired
    private final AddressRepository addressRepository;

    @Autowired
    private final WarehouseClient warehouseClient;

    @Autowired
    private final OrderClient orderClient;

    @Override
    public DeliveryDto put(DeliveryDto deliveryDto) {
        if (deliveryDto.getDeliveryId() != null && repository.findById(deliveryDto.getDeliveryId()).isPresent()) {
            throw new DeliveryIsAlreadyContainException(String.format("Доставка с id = %s уже существует", deliveryDto.getDeliveryId()));
        }
        Address toAddress = addressRepository.findOne(Example.of(AddressMapper.toModel(deliveryDto.getToAddress())))
                .orElse(addressRepository.save(AddressMapper.toModel(deliveryDto.getToAddress())));
        Address fromAddress = addressRepository.findOne(Example.of(AddressMapper.toModel(deliveryDto.getFromAddress())))
                .orElse(addressRepository.save(AddressMapper.toModel(deliveryDto.getFromAddress())));
        Delivery delivery = DeliveryMapper.toModel(deliveryDto);
        delivery.setToAddress(toAddress);
        delivery.setFromAddress(fromAddress);
        delivery.setDeliveryState(DeliveryState.CREATED);
        return DeliveryMapper.toDto(repository.save(delivery));
    }

    @Override
    public void successDelivery(String deliveryId) {
        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow(() -> new NoDeliveryFoundException(String.format(deliveryNotFoundErrorMessage, deliveryId)));
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        orderClient.deliverySuccess(delivery.getOrderId());
        repository.save(delivery);

    }

    @Override
    public void receivingProduct(String deliveryId) {
        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow(() -> new NoDeliveryFoundException(String.format(deliveryNotFoundErrorMessage, deliveryId)));
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        warehouseClient.shippedToDelivery(ShippedToDeliveryRequest.builder()
                .orderId(delivery.getOrderId())
                .deliveryId(deliveryId)
                .build());
        repository.save(delivery);
    }

    @Override
    public void failDelivery(String deliveryId) {
        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow(() -> new NoDeliveryFoundException(String.format(deliveryNotFoundErrorMessage, deliveryId)));
        delivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.deliveryFailed(delivery.getOrderId());
        repository.save(delivery);
    }

    @Override
    public BigDecimal calculateDeliveryCost(OrderDto order) {
        if (order.getDeliveryId() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Нельзя рассчитать стоимость без идентификатора доставки");
        }
        Delivery delivery = repository.findById(order.getDeliveryId())
                .orElseThrow(() -> new NoDeliveryFoundException(String.format(deliveryNotFoundErrorMessage, order.getDeliveryId())));
        BigDecimal price = new BigDecimal(5);
        if (warehouseClient.getAddress().getCity().equals("ADDRESS_2")) {
            price = price.multiply(new BigDecimal(3));
        } else {
            price = price.multiply(new BigDecimal(2));
        }
        if (order.getDeliveryWeight() == null || order.getFragile() == null || order.getDeliveryVolume() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Значения веса, объема и хрупкости не могут быть пустыми");
        }
        if (order.getFragile()) {
            price = price.add(price.multiply(new BigDecimal("0.2")));
        }
        price = price.add(new BigDecimal(order.getDeliveryWeight() * 0.3));
        price = price.add(new BigDecimal(order.getDeliveryVolume() * 0.2));
        if (!warehouseClient.getAddress().getStreet().equals(delivery.getFromAddress().getStreet())) {
            price = price.add(price.multiply(new BigDecimal("0.2")));
        }
        return price;
    }
}
