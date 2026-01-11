package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.service.ShoppingStoreService;
import ru.yandex.practicum.store.dto.ProductCategory;
import ru.yandex.practicum.store.dto.ProductDto;
import ru.yandex.practicum.store.dto.QuantityState;
import ru.yandex.practicum.store.dto.SetProductQuantityStateRequest;


@RestController
@RequestMapping("/api/v1/shopping-store")
@AllArgsConstructor
public class ShoppingStoreController {

    @Autowired
    private final ShoppingStoreService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDto> getByCategory(@RequestParam(name = "category") ProductCategory category, @PageableDefault(sort = {"productName"}) Pageable pageable) {
        return service.getByCategory(category, pageable);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto put(@RequestBody @Valid ProductDto productDto) {
        return service.put(productDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto update(@RequestBody @Valid ProductDto productDto) {
        return service.update(productDto);
    }

    @PostMapping("/removeProductFromStore")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto delete(@RequestBody Object productId) {
        return service.delete((String) productId);
    }

    @PostMapping("/quantityState")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto changeProductQuantityStatus(@Valid @RequestBody SetProductQuantityStateRequest request) {
        return service.changeProductQuantityStatus(request);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable("productId") String productId) {
        return service.get(productId);
    }
}
