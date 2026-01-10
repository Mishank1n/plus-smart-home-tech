package ru.yandex.practicum.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.AddressDto;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewOrderRequest {

    @NotNull
    @Valid
    ShoppingCartDto shoppingCart;

    @NotNull
    @Valid
    AddressDto deliveryAddress;

    @NotBlank
    String username;
}
