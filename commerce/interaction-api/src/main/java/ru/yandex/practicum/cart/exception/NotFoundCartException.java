package ru.yandex.practicum.cart.exception;

public class NotFoundCartException extends RuntimeException {
    public NotFoundCartException(String message) {
        super(message);
    }
}
