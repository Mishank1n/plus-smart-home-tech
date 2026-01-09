package ru.yandex.practicum.cart.exception;

public class CartIsAlreadyInShop extends RuntimeException {
    public CartIsAlreadyInShop(String message) {
        super(message);
    }
}
