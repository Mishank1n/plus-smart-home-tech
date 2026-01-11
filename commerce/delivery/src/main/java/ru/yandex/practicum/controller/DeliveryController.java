package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.delivery.dto.DeliveryDto;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;

@RestController
@RequestMapping(DeliveryController.PATH)
@AllArgsConstructor
public class DeliveryController {

    public final static String PATH = "/api/v1/delivery";

    @Autowired
    private final DeliveryService service;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DeliveryDto put(@Valid @RequestBody DeliveryDto delivery) {
        return service.put(delivery);
    }

    @PostMapping("/successful")
    @ResponseStatus(HttpStatus.OK)
    public void successDelivery(@RequestBody @UUID @NotBlank String deliveryId) {
        service.successDelivery(deliveryId);
    }

    @PostMapping("/picked")
    @ResponseStatus(HttpStatus.OK)
    public void receivingProduct(@RequestBody @UUID @NotBlank String deliveryId) {
        service.receivingProduct(deliveryId);
    }

    @PostMapping("/failed")
    @ResponseStatus(HttpStatus.OK)
    public void failDelivery(@RequestBody @UUID @NotBlank String deliveryId) {
        service.failDelivery(deliveryId);
    }

    @PostMapping("/cost")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateDeliveryCost(@Valid @RequestBody OrderDto order) {
        return service.calculateDeliveryCost(order);
    }
}
