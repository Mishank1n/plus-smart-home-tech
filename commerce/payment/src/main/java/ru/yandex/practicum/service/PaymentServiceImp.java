package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.order.client.OrderClient;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.payment.dto.PaymentDto;
import ru.yandex.practicum.payment.dto.PaymentState;
import ru.yandex.practicum.payment.exception.NoPaymentFoundException;
import ru.yandex.practicum.payment.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.store.client.ShoppingStoreClient;
import ru.yandex.practicum.store.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Map;

@Service
@AllArgsConstructor
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private final PaymentRepository repository;

    @Autowired
    private final ShoppingStoreClient shoppingStoreClient;

    @Autowired
    private final OrderClient orderClient;

    @Override
    public BigDecimal calculateProductsCost(OrderDto order) {
        BigDecimal productPrice = BigDecimal.valueOf(0);
        for (Map.Entry<String, Integer> product : order.getProducts().entrySet()) {
            ProductDto productDto = shoppingStoreClient.getProduct(product.getKey());
            productPrice = productPrice.add(productDto.getPrice().multiply(new BigDecimal(product.getValue())));
        }
        return productPrice;
    }

    @Override
    public BigDecimal calculateTotalCost(OrderDto order) {
        if (order.getDeliveryPrice() == null || order.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Отсутствуют поля итоговых сумм для доставки и товаров");
        }
        return order.getDeliveryPrice().add(order.getProductPrice().multiply(new BigDecimal("1.1")));
    }

    @Override
    public void successPayment(String orderId) {
        Payment payment = repository.findByOrderId(orderId).orElseThrow(() ->
                new NoPaymentFoundException(String.format("Оплата для заказа с id = %s не найдена", orderId)));
        payment.setPaymentState(PaymentState.SUCCESS);
        orderClient.paymentSuccess(orderId);
    }

    @Override
    public void failPayment(String orderId) {
        Payment payment = repository.findByOrderId(orderId).orElseThrow(() ->
                new NoPaymentFoundException(String.format("Оплата для заказа с id = %s не найдена", orderId)));
        payment.setPaymentState(PaymentState.FAILED);
        orderClient.paymentFailed(orderId);
    }

    @Override
    public PaymentDto put(OrderDto order) {
        if (order.getTotalPrice() == null || order.getDeliveryPrice() == null || order.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно данных для оплаты заказа");
        }
        Payment payment = new Payment();
        payment.setPaymentState(PaymentState.PENDING);
        payment.setDeliveryTotal(order.getDeliveryPrice());
        payment.setFeeTotal(order.getProductPrice().multiply(new BigDecimal("0.1")));
        payment.setTotalPayment(calculateTotalCost(order));
        payment.setProductTotal(calculateProductsCost(order));
        payment.setOrderId(order.getOrderId());
        payment = repository.save(payment);
        return PaymentMapper.toDto(payment);
    }
}