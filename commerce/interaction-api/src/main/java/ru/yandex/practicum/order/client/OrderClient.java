package ru.yandex.practicum.order.client;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.order.dto.OrderDto;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderClient {

    @PostMapping("/payment/success")
    OrderDto paymentSuccess(@RequestBody @NotBlank @UUID String orderId);

    @PostMapping("/payment/failed")
    OrderDto paymentFailed(@RequestBody @NotBlank @UUID String orderId);

    @PostMapping("/delivery")
    OrderDto delivery(@RequestBody @NotBlank @UUID String orderId);

    @PostMapping("/delivery/failed")
    OrderDto deliveryFailed(@RequestBody @NotBlank @UUID String orderId);

    @PostMapping("/delivery/success")
    OrderDto deliverySuccess(@RequestBody @NotBlank @UUID String orderId);
}
