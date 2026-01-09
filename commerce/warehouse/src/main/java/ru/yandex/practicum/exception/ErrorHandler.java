package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.exeption.ExceptionResponse;
import ru.yandex.practicum.warehouse.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.warehouse.exception.SpecifiedProductAlreadyInWarehouseException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handlerAnotherException(RuntimeException e) {
        System.out.println(e.getMessage());
        return new ExceptionResponse("Внутренняя ошибка сервера");
    }

    @ExceptionHandler({SpecifiedProductAlreadyInWarehouseException.class, NoSpecifiedProductInWarehouseException.class, ProductInShoppingCartLowQuantityInWarehouseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerBadRequest(RuntimeException e) {
        return new ExceptionResponse(e.getMessage());
    }
}
