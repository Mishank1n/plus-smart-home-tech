package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(ShoppingCartController.PATH)
@AllArgsConstructor
public class ShoppingCartController {

    public final static String PATH = "/api/v1/shopping-cart";

    @Autowired
    private final ShoppingCartService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto get(@RequestParam(name = "username") @NotBlank String userName) {
        return service.getCart(userName);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto put(@RequestParam(name = "username") @NotBlank String userName, @RequestBody Map<String, Integer> products) {
        return service.put(userName, products);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(name = "username") @NotBlank String userName) {
        service.delete(userName);
    }

    @PostMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto removeItems(@RequestParam(name = "username") @NotBlank String userName, ArrayList<String> productIds) {
        return service.removeItems(userName, productIds);
    }

    @PostMapping("/change-quantity")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto changeQuantityForItem(@RequestParam(name = "username") @NotBlank String userName, @RequestBody @Valid ChangeProductQuantityRequest request) {
        return service.changeQuantityForItem(userName, request);
    }
}