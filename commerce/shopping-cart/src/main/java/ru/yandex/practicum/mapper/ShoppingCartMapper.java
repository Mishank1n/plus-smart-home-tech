package ru.yandex.practicum.mapper;

import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.model.ShoppingCart;

public class ShoppingCartMapper {

    public static ShoppingCart toShoppingCarCreate(String owner) {
        return ShoppingCart.builder().owner(owner).build();
    }

    public static ShoppingCartDto toDto(ShoppingCart shoppingCart) {
        return ShoppingCartDto.builder()
                .shoppingCartId(shoppingCart.getShoppingCartId())
                .products(ShoppingCartItemMapper.toMap(shoppingCart.getShoppingCartItems()))
                .build();
    }
}
