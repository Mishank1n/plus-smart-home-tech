package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.order.dto.CreateNewOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;


@RestController
@RequestMapping(OrderController.PATH)
@AllArgsConstructor
public class OrderController {

    public final static String PATH = "/api/v1/order";

    @Autowired
    private final OrderService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> getAll(@RequestParam(name = "username") @NotBlank String username, @PageableDefault(sort = {"username"}) Pageable pageable) {
        return service.getAll(username, pageable);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDto put(@RequestBody @Valid CreateNewOrderRequest request) {
        return service.put(request);
    }

    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto productReturn(@RequestBody @Valid ProductReturnRequest request) {
        return service.productReturn(request);
    }

    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto payment(@RequestBody @NotBlank @UUID String orderId) {
        return service.payment(orderId);
    }

    @PostMapping("/payment/success")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto paymentSuccess(@RequestBody @NotBlank @UUID String orderId) {
        return service.paymentSuccess(orderId);
    }

    @PostMapping("/payment/failed")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto paymentFailed(@RequestBody @NotBlank @UUID String orderId) {
        return service.paymentFailed(orderId);
    }

    @PostMapping("/delivery")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto delivery(@RequestBody @NotBlank @UUID String orderId) {
        return service.delivery(orderId);
    }

    @PostMapping("/delivery/success")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto deliverySuccess(@RequestBody @NotBlank @UUID String orderId) {
        return service.deliverySuccess(orderId);
    }

    @PostMapping("/delivery/failed")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto deliveryFailed(@RequestBody @NotBlank @UUID String orderId) {
        return service.deliveryFailed(orderId);
    }

    @PostMapping("/completed")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto orderComplete(@RequestBody @NotBlank @UUID String orderId) {
        return service.orderComplete(orderId);
    }

    @PostMapping("/calculate/total")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto calculateTotal(@RequestBody @NotBlank @UUID String orderId) {
        return service.calculateTotal(orderId);
    }

    @PostMapping("/calculate/delivery")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto calculateDelivery(@RequestBody @NotBlank @UUID String orderId) {
        return service.calculateDelivery(orderId);
    }

    @PostMapping("/assembly")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto assemblySuccess(@RequestBody @NotBlank @UUID String orderId) {
        return service.assemblySuccess(orderId);
    }

    @PostMapping("/assembly/failed")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto assemblyFailed(@RequestBody @NotBlank @UUID String orderId) {
        return service.assemblyFailed(orderId);
    }

}
