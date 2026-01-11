package ru.yandex.practicum.payment.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.payment.dto.PaymentDto;

import java.math.BigDecimal;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient {

    @PostMapping("/productCost")
    BigDecimal calculateProductsCost(@Valid @RequestBody OrderDto order);

    @PostMapping("/totalCost")
    BigDecimal calculateTotalCost(@Valid @RequestBody OrderDto order);

    @PostMapping
    PaymentDto put(@Valid @RequestBody OrderDto order);
}
