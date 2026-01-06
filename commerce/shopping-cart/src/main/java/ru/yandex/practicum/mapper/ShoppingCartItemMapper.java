package ru.yandex.practicum.mapper;

import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.model.ShoppingCartItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartItemMapper {

    public static List<ShoppingCartItem> toList(ShoppingCart shoppingCart, Map<String, Integer> products) {
        List<ShoppingCartItem> cartItems = new ArrayList<>();
        for (Map.Entry<String, Integer> product : products.entrySet()) {
            cartItems.add(ShoppingCartItem.builder().
                    shoppingCart(shoppingCart)
                    .productId(product.getKey())
                    .quantity(product.getValue())
                    .build());
        }
        return cartItems;
    }

    public static Map<String, Integer> toMap(List<ShoppingCartItem> cartItems) {
        Map<String, Integer> products = new HashMap<>();
        cartItems.forEach(
                shoppingCartItem -> products.put(shoppingCartItem.getProductId(), shoppingCartItem.getQuantity())
        );
        return products;
    }
}
