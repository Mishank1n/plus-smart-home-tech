package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookedProductsDto {

    @Min(value = 0)
    @Builder.Default
    Double deliveryWeight = 0.0;

    @Min(value = 0)
    @Builder.Default
    Double deliveryVolume = 0.0;

    @NotNull
    @Builder.Default
    Boolean fragile = false;
}
