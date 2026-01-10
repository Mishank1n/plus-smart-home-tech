package ru.yandex.practicum.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.cart.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.cart.exception.NotFoundCartException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handlerAnotherException(RuntimeException e) {
        System.out.println(e.getMessage());
        return new ExceptionResponse("Внутренняя ошибка сервера");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handlerBadUserName(ValidationException e) {
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler({NotFoundCartException.class, NoProductsInShoppingCartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerBadRequest(RuntimeException e) {
        return new ExceptionResponse(e.getMessage());
    }
}
