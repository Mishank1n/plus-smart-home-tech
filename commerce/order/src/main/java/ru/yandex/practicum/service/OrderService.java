package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.order.dto.CreateNewOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.ProductReturnRequest;


public interface OrderService {

    Page<OrderDto> getAll(String username, Pageable pageable);

    OrderDto put(CreateNewOrderRequest request);

    OrderDto productReturn(ProductReturnRequest request);

    OrderDto payment(String orderId);

    OrderDto paymentSuccess(String orderId);

    OrderDto paymentFailed(String orderId);

    OrderDto delivery(String orderId);

    OrderDto deliverySuccess(String orderId);

    OrderDto deliveryFailed(String orderId);

    OrderDto orderComplete(String orderId);

    OrderDto calculateTotal(String orderId);

    OrderDto calculateDelivery(String orderId);

    OrderDto assemblySuccess(String orderId);

    OrderDto assemblyFailed(String orderId);
}
