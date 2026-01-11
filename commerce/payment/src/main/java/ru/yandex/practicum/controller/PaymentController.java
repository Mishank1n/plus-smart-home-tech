package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.payment.dto.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;

@RestController
@RequestMapping(PaymentController.PATH)
@AllArgsConstructor
public class PaymentController {

    public static final String PATH = "/api/v1/payment";

    @Autowired
    private final PaymentService service;


    @PostMapping("/productCost")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateProductsCost(@Valid @RequestBody OrderDto order) {
        return service.calculateProductsCost(order);
    }

    @PostMapping("/totalCost")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateTotalCost(@Valid @RequestBody OrderDto order) {
        return service.calculateTotalCost(order);
    }

    @PostMapping("/refund")
    @ResponseStatus(HttpStatus.OK)
    public void successPayment(@RequestBody @NotBlank @UUID String paymentId) {
        service.successPayment(paymentId);
    }

    @PostMapping("/failed")
    @ResponseStatus(HttpStatus.OK)
    public void failPayment(@RequestBody @NotBlank @UUID String paymentId) {
        service.failPayment(paymentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentDto put(@Valid @RequestBody OrderDto order) {
        return service.put(order);
    }

}
