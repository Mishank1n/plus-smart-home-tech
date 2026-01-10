package ru.yandex.practicum.service;

import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.order.dto.OrderBookingDto;
import ru.yandex.practicum.warehouse.dto.*;

import java.util.Map;

public interface WarehouseService {

    void put(NewProductInWarehouseRequest request);

    AddressDto getAddress();

    void addQuantity(AddProductToWarehouseRequest addRequest);

    void shippedToDelivery(ShippedToDeliveryRequest request);

    BookedProductsDto checkQuantityByCart(ShoppingCartDto cartDto);

    void getProductsFromReturn(Map<String, Integer> products);

    OrderBookingDto assemblyProductForOrder(AssemblyProductsForOrderRequest request);
}
