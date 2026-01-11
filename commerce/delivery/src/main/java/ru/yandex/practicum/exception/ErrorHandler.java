package ru.yandex.practicum.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.delivery.exception.DeliveryIsAlreadyContainException;
import ru.yandex.practicum.delivery.exception.NoDeliveryFoundException;
import ru.yandex.practicum.payment.exception.NotEnoughInfoInOrderToCalculateException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFound(NoDeliveryFoundException e) {
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler({ValidationException.class, DeliveryIsAlreadyContainException.class, NotEnoughInfoInOrderToCalculateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequest(RuntimeException e) {
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleServerError(RuntimeException e) {
        System.out.println(e.getMessage());
        return new ExceptionResponse("Внутренняя ошибка сервера");
    }
}