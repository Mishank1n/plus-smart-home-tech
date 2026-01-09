package ru.yandex.practicum.service;

import ru.yandex.practicum.cart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;

public interface ShoppingCartService {

    ShoppingCartDto getCart(String userName);

    ShoppingCartDto put(String userName, Map<String, Integer> products);

    void delete(String userName);

    ShoppingCartDto removeItems(String userName, List<String> productIds);

    ShoppingCartDto changeQuantityForItem(String userName, ChangeProductQuantityRequest request);
}
