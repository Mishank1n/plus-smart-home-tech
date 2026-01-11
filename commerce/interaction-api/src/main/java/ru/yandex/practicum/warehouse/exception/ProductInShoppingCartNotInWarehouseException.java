package ru.yandex.practicum.warehouse.exception;

public class ProductInShoppingCartNotInWarehouseException extends RuntimeException {
    public ProductInShoppingCartNotInWarehouseException(String message) {
        super(message);
    }
}
