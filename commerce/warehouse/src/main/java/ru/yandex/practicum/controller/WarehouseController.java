package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;

@RestController
@RequestMapping(WarehouseController.PATH)
@Slf4j
@AllArgsConstructor
public class WarehouseController {

    public final static String PATH = "/api/v1/warehouse";

    @Autowired
    private final WarehouseService service;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void put(@Valid @RequestBody NewProductInWarehouseRequest request) {
        service.put(request);
    }

    @GetMapping("/address")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddress() {
        return service.getAddress();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void addQuantity(@Valid @RequestBody AddProductToWarehouseRequest addRequest) {
        service.addQuantity(addRequest);
    }

    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public BookedProductsDto checkQuantity(@Valid @RequestBody ShoppingCartDto cartDto) {
        return service.checkQuantity(cartDto);
    }
}
