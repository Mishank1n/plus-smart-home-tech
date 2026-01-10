package ru.yandex.practicum.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderBookingDto {

    @NotEmpty
    Map<String, Integer> products;

    @NotBlank
    String orderId;

    @NotNull
    BookedProductsDto bookedProducts;
}