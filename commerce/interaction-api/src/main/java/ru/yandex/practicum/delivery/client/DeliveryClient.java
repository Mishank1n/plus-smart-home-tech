package ru.yandex.practicum.delivery.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.delivery.dto.DeliveryDto;
import ru.yandex.practicum.order.dto.OrderDto;

import java.math.BigDecimal;

@FeignClient(name = "delivery", path = "/api/v1/delivery")
public interface DeliveryClient {

    @PutMapping
    DeliveryDto put(@Valid @RequestBody DeliveryDto delivery);

    @PostMapping("/cost")
    BigDecimal calculateDeliveryCost(@Valid @RequestBody OrderDto order);
}
