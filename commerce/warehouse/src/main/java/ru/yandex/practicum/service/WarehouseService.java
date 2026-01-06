package ru.yandex.practicum.service;

import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;

public interface WarehouseService {

    void put(NewProductInWarehouseRequest request);

    AddressDto getAddress();

    void addQuantity(AddProductToWarehouseRequest addRequest);

    BookedProductsDto checkQuantity(ShoppingCartDto cartDto);
}
