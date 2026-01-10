package ru.yandex.practicum.delivery.exception;

public class DeliveryIsAlreadyContainException extends RuntimeException {
    public DeliveryIsAlreadyContainException(String message) {
        super(message);
    }
}
