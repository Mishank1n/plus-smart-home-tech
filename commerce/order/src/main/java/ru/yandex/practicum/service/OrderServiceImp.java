package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.delivery.client.DeliveryClient;
import ru.yandex.practicum.delivery.dto.DeliveryDto;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.order.dto.CreateNewOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.OrderState;
import ru.yandex.practicum.order.dto.ProductReturnRequest;
import ru.yandex.practicum.order.exception.NoOrderFoundException;
import ru.yandex.practicum.payment.client.PaymentClient;
import ru.yandex.practicum.payment.dto.PaymentDto;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.warehouse.client.WarehouseClient;
import ru.yandex.practicum.warehouse.dto.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;


    @Autowired
    private final DeliveryClient deliveryClient;

    @Autowired
    private final PaymentClient paymentClient;

    @Autowired
    private final WarehouseClient warehouseClient;


    @Override
    public Page<OrderDto> getAll(String username, Pageable pageable) {
        return orderRepository.findAllByUsername(username, pageable).map(OrderMapper::toDto);
    }

    @Override
    public OrderDto put(CreateNewOrderRequest request) {
        Order order = new Order();
        order.setShoppingCartId(request.getShoppingCart().getShoppingCartId());
        order.setUsername(request.getUsername());
        order.setState(OrderState.NEW);
        order.setProducts(request.getShoppingCart().getProducts());
        order = orderRepository.save(order);
        DeliveryDto delivery = deliveryClient.put(DeliveryDto.builder()
                .fromAddress(warehouseClient.getAddress())
                .toAddress(request.getDeliveryAddress())
                .orderId(order.getOrderId())
                .build());
        order.setDeliveryId(delivery.getDeliveryId());
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto productReturn(ProductReturnRequest request) {
        Order order = findById(request.getOrderId());
        order.setState(OrderState.PRODUCT_RETURNED);
        request.getProducts().forEach((productId, quantity) -> {
            int newQuantity = order.getProducts().get(productId) - quantity;
            if (newQuantity <= 0) {
                order.getProducts().remove(productId);
            } else {
                order.getProducts().put(productId, newQuantity);
            }
        });
        orderRepository.save(order);
        warehouseClient.getProductsFromReturn(request.getProducts());
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto payment(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.ON_PAYMENT);
        PaymentDto payment = paymentClient.put(OrderMapper.toDto(order));
        order.setTotalPrice(payment.getTotalPayment());
        order.setDeliveryPrice(payment.getDeliveryTotal());
        order.setProductPrice(payment.getProductTotal());
        order.setPaymentId(payment.getPaymentId());
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto paymentSuccess(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.PAID);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto paymentFailed(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.PAYMENT_FAILED);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto delivery(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.ON_DELIVERY);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto deliverySuccess(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.DELIVERED);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto deliveryFailed(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.DELIVERY_FAILED);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto orderComplete(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.COMPLETED);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto calculateTotal(String orderId) {
        Order order = findById(orderId);
        BigDecimal totalPayment = paymentClient.calculateTotalCost(OrderMapper.toDto(order));
        order.setTotalPrice(totalPayment);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto calculateDelivery(String orderId) {
        Order order = findById(orderId);
        BigDecimal deliveryCost = deliveryClient.calculateDeliveryCost(OrderMapper.toDto(order));
        order.setDeliveryPrice(deliveryCost);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto assemblySuccess(String orderId) {
        Order order = findById(orderId);
        AssemblyProductsForOrderRequest request = new AssemblyProductsForOrderRequest(order.getProducts(), orderId);

        BookedProductsDto bookedProducts = warehouseClient.assemblyProductForOrderFromShoppingCart(request).getBookedProducts();
        order.setState(OrderState.ASSEMBLED);
        order.setDeliveryWeight(bookedProducts.getDeliveryWeight());
        order.setFragile(bookedProducts.getFragile());
        order.setDeliveryVolume(bookedProducts.getDeliveryVolume());
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto assemblyFailed(String orderId) {
        Order order = findById(orderId);
        order.setState(OrderState.ASSEMBLY_FAILED);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    private Order findById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new NoOrderFoundException(String.format("Заказ с id = %s не найден", orderId)));
    }
}
