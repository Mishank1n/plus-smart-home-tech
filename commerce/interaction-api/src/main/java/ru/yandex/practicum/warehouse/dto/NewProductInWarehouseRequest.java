package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewProductInWarehouseRequest {

    @UUID
    @NotBlank
    String productId;

    @NotNull
    Boolean fragile;

    @NotNull
    DimensionDto dimension;

    @Min(value = 1)
    @NotNull
    Double weight;
}
